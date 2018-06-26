/*
 * Copyright © 2013-2016 The XAX Core Developers.
 * Copyright © 2016-2017 Jelurida IP B.V.
 *
 * See the LICENSE.txt file at the top-level directory of this distribution
 * for licensing information.
 *
 * Unless otherwise agreed in a custom licensing agreement with Jelurida B.V.,
 * no part of the XAX software, including this file, may be copied, modified,
 * propagated, or distributed except according to the terms contained in the
 * LICENSE.txt file.
 *
 * Removal or modification of this copyright notice is prohibited.
 *
 */

package xax;

import xax.db.BasicDb;
import xax.db.TransactionalDb;

public final class Db {

    public static final String PREFIX = Constants.isTestnet ? "xax.testDb" : "xax.db";
    public static final TransactionalDb db = new TransactionalDb(new BasicDb.DbProperties()
            .maxCacheSize(XAX.getIntProperty("xax.dbCacheKB"))
            .dbUrl(XAX.getStringProperty(PREFIX + "Url"))
            .dbType(XAX.getStringProperty(PREFIX + "Type"))
            .dbDir(XAX.getStringProperty(PREFIX + "Dir"))
            .dbParams(XAX.getStringProperty(PREFIX + "Params"))
            .dbUsername(XAX.getStringProperty(PREFIX + "Username"))
            .dbPassword(XAX.getStringProperty(PREFIX + "Password", null, true))
            .maxConnections(XAX.getIntProperty("xax.maxDbConnections"))
            .loginTimeout(XAX.getIntProperty("xax.dbLoginTimeout"))
            .defaultLockTimeout(XAX.getIntProperty("xax.dbDefaultLockTimeout") * 1000)
            .maxMemoryRows(XAX.getIntProperty("xax.dbMaxMemoryRows"))
    );

    static void init() {
        db.init(new XAXDbVersion());
    }

    static void shutdown() {
        db.shutdown();
    }

    private Db() {} // never

}
