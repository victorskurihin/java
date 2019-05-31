package su.svn.fi.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class YamlApplicationProperties
{
    private int batchSize = 10;

    public int getBatchSize()
    {
        return batchSize;
    }

    public void setBatchSize(int batchSize)
    {
        this.batchSize = batchSize;
    }
}
