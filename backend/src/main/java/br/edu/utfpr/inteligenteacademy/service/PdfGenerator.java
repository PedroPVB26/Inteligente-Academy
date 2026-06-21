package br.edu.utfpr.inteligenteacademy.service;

import br.edu.utfpr.inteligenteacademy.config.AppProperties;
import br.edu.utfpr.inteligenteacademy.entity.Certificate;
import br.edu.utfpr.inteligenteacademy.entity.Enrollment;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.Margin;
import com.microsoft.playwright.options.Media;
import com.microsoft.playwright.options.WaitUntilState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class PdfGenerator {
    private final AppProperties appProperties;

    public byte[] generatePdf(Long enrollmentId) {

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );

            Page page = browser.newPage();

            page.setExtraHTTPHeaders(
                    Map.of("X-Internal-Token", appProperties.getInternalToken())
            );

            String url = appProperties.getBaseUrl()
                    + "/internal/certificates/"
                    + enrollmentId;

            page.navigate(url, new Page.NavigateOptions()
                    .setWaitUntil(WaitUntilState.LOAD)
            );

            page.emulateMedia(new Page.EmulateMediaOptions()
                    .setMedia(Media.SCREEN));

            page.waitForLoadState(LoadState.LOAD);
            page.waitForTimeout(1000);

            byte[] pdf = page.pdf(new Page.PdfOptions()
                    .setFormat("A4") // Define o tamanho exato da página
                    .setLandscape(true) // Força a orientação deitada (Paisagem)
                    .setPrintBackground(true) // Mantém as cores de fundo
                    .setMargin(new Margin().setTop("0").setRight("0").setBottom("0").setLeft("0")) // Remove qualquer margem branca do PDF
            );

            browser.close();
            return pdf;
        }
    }
}