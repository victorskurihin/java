package ru.otus.l151.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author v.chibrikov
 */
class TemplateProcessor {
    private static final String HTML_DIR = "/templates/";
    private static TemplateProcessor instance = new TemplateProcessor();

    private final Configuration configuration;

    private TemplateProcessor() {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(this.getClass(), HTML_DIR);
    }

    static TemplateProcessor instance() {
        return instance;
    }

    String getPage(String filename, Map<String, Object> data) throws IOException {
        try (Writer stream = new StringWriter();) {

            Template template = configuration.getTemplate(filename);
            template.process(data, stream);

            return stream.toString();
        } catch (TemplateException e) {
            throw new IOException(e);
        }
    }
}

