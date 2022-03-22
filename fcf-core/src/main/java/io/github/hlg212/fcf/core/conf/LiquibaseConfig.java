package  io.github.hlg212.fcf.core.conf;

import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.ChangeLogParseException;
import liquibase.parser.core.xml.XMLChangeLogSAXParser;
import liquibase.resource.ResourceAccessor;

import java.util.Iterator;
/**
 * liquibase目录修正
 *
 * @author  huangligui
 * @create: 2019-03-01 13:23
 */
public class LiquibaseConfig {

    public static class ChangeLogParser extends XMLChangeLogSAXParser
    {
        private final static String BOOT_CLASSES = "BOOT-INF/classes/";
        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public DatabaseChangeLog parse(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, ResourceAccessor resourceAccessor) throws ChangeLogParseException {
            DatabaseChangeLog log =  super.parse(physicalChangeLogLocation, changeLogParameters, resourceAccessor);
            Iterator<ChangeSet> iterator =  log.getChangeSets().iterator();
            while( iterator.hasNext() )
            {
                ChangeSet set =  iterator.next();
                String filePath = set.getFilePath();
                if( filePath.startsWith(BOOT_CLASSES) )
                {
                    set.setFilePath(filePath.substring(BOOT_CLASSES.length()));
                }
            }
            return log;
        }
    }
}
