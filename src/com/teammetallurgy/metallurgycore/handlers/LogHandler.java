package com.teammetallurgy.metallurgycore.handlers;

import java.util.logging.Logger;

public class LogHandler
{

    private static Logger log;

    public static void log(String message)
    {
        if (LogHandler.log != null)
        {
            LogHandler.log.warning(message);
        }
    }

    public static void setLog(Logger logger)
    {
        LogHandler.log = logger;
    }

}
