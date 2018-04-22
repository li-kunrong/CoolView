package com.coolview.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.xiaoleilu.hutool.io.FileUtil;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;
import com.xiaoleilu.hutool.setting.dialect.Props;

/**
 * 配置管理
 * @author 61400
 *
 */
public class Config {
    

    private static final Log logger = LogFactory.get();

    private File file;

    private Props props;

    private static Config ourInstance = new Config();
    
    private String theme;

    private String font;

    private int fontSize;

    public static Config getInstance() {
        return ourInstance;
    }
    
    public Config() {

        // --旧版本配置文件迁移初始化开始--
        File configHomeDir = new File(SystemUtil.configHome);
        if (!configHomeDir.exists()) {
            configHomeDir.mkdirs();
        }
        File oldConfigDir = new File("config");
        if (oldConfigDir.exists()) {
            FileUtil.copy(oldConfigDir.getAbsolutePath(), SystemUtil.configHome, true);
            FileUtil.del(oldConfigDir.getAbsolutePath());
        }

        File oldDataDir = new File("data");
        if (oldDataDir.exists()) {
            FileUtil.copy(oldDataDir.getAbsolutePath(), SystemUtil.configHome, true);
            FileUtil.del(oldDataDir.getAbsolutePath());
        }
        // --旧版本配置文件迁移初始化结束--

        file = new File(SystemUtil.configHome + "config" + File.separator + "config.properties");
        File configDir = new File(SystemUtil.configHome + "config" + File.separator);
        if (!file.exists()) {
            try {
                configDir.mkdirs();
                file.createNewFile();
                originInit();
            } catch (IOException e) {
                logger.error(e);
            }
        }
        props = new Props(file);
    }
    
    /**
     * 存盘
     */
    public void save() {
        try {
            props.store(new FileOutputStream(file), null);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    private void originInit() {

        props = new Props(file);

        props.setProperty("setting.appearance.theme", "Darcula(推荐)");
        props.setProperty("setting.appearance.font", "Microsoft YaHei UI");
        props.setProperty("setting.appearance.fontSize", "18");

        save();   
    }
    
    public String getTheme() {
        return props.getProperty("setting.appearance.theme");
    }

    public void setTheme(String theme) {
        props.setProperty("setting.appearance.theme", theme);
    }

    public String getFont() {
        return props.getProperty("setting.appearance.font");
    }

    public void setFont(String font) {
        props.setProperty("setting.appearance.font", font);
    }

    public int getFontSize() {
        return props.getInt("setting.appearance.fontSize");
    }

    public void setFontSize(int fontSize) {
        props.setProperty("setting.appearance.fontSize", fontSize);
    }


    public void setProps(String key, String value) {
        props.setProperty(key, value);
    }

    public String getProps(String key) {
        return props.getProperty(key);
    }
    

}
