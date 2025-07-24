package com.Livraria_Digital.scrapping;

import com.Livraria_Digital.dto.LivroDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Logger;

@Service
public class LivroScrapingService {

    private final Logger logger = Logger.getLogger(LivroScrapingService.class.getName());

    public LivroDTO extrairDadosLivro(String url) throws IOException {
        logger.info("Iniciando scraping da URL: " + url);

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Connection", "keep-alive")
                    .timeout(10_000)
                    .get();

            // Extrair título
            String titulo = doc.select("#productTitle").text();
            if (titulo == null || titulo.isBlank()) {
                throw new IOException("Título não encontrado na página");
            }
            logger.info("Título extraído: " + titulo);

            // Extrair preço
            Elements precoElements = doc.select(".a-price-whole");
            if (precoElements.isEmpty()) {
                throw new IOException("Preço não encontrado na página");
            }
            String precoStr = precoElements.first().text().replaceAll("[^\\d]", "");
            logger.info("Preço extraído (string): " + precoStr);

            BigDecimal preco;
            try {
                preco = new BigDecimal(precoStr); // Ajusta centavos
            } catch (NumberFormatException e) {
                throw new IOException("Formato de preço inválido: " + precoStr);
            }

            // Extrair ano de publicação
            String anoStr = doc.select("#detailBullets_feature_div li:contains(Publicação)").text();
            int anoPublicacao = extrairAno(anoStr);
            if (anoPublicacao == 0) {
                logger.warning("Ano de publicação não encontrado ou inválido, definido como 0");
            } else {
                logger.info("Ano de publicação extraído: " + anoPublicacao);
            }

            // Extrair ISBN a partir da URL
            String isbn = extrairIsbn(url);
            if (isbn == null || isbn.isBlank()) {
                logger.warning("Nenhum ISBN válido encontrado na URL.");
            } else {
                logger.info("ISBN extraído: " + isbn);
            }

            LivroDTO dto = new LivroDTO();
            dto.setTitulo(titulo);
            dto.setPreco(preco);
            dto.setAnoPublicacao(anoPublicacao);
            dto.setIsbn(isbn);

            return dto;

        } catch (IOException e) {
            logger.warning("Erro durante scraping: " + e.getMessage());
            throw e;
        }
    }

    private int extrairAno(String texto) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\b(\\d{4})\\b").matcher(texto);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    public String extrairIsbn(String url) {
        try {
            if (url.contains("/dp/")) {
                String asin = url.substring(url.indexOf("/dp/") + 4);
                int slash = asin.indexOf("/");
                if (slash != -1) asin = asin.substring(0, slash);
                return asin;
            }
        } catch (Exception e) {
            // log de erro
        }
        return null;
    }
}
