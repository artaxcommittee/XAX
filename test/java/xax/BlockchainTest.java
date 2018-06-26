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

import xax.util.Logger;
import xax.util.Time;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.util.Properties;

public abstract class BlockchainTest extends AbstractBlockchainTest {

    protected static Tester FORGY;
    protected static Tester ALICE;
    protected static Tester BOB;
    protected static Tester CHUCK;
    protected static Tester DAVE;

    protected static int baseHeight;

    protected static String forgerSecretPhrase = "aSykrgKGZNlSVOMDxkZZgbTvQqJPGtsBggb";
    protected static final String forgerAccountId = "XAX-9KZM-KNYY-QBXZ-5TD8V";
    protected static String secretPhrase1 = "hope peace happen touch easy pretend worthless talk them indeed wheel state";
    protected static String secretPhrase2 = "rshw9abtpsa2";
    protected static String secretPhrase3 = "eOdBVLMgySFvyiTy8xMuRXDTr45oTzB7L5J";
    protected static String secretPhrase4 = "t9G2ymCmDsQij7VtYinqrbGCOAtDDA3WiNr";

    private static final String aliceSecretPhrase = "hope peace happen touch easy pretend worthless talk them indeed wheel state";
    private static final String bobSecretPhrase2 = "rshw9abtpsa2";
    private static final String chuckSecretPhrase = "eOdBVLMgySFvyiTy8xMuRXDTr45oTzB7L5J";
    private static final String daveSecretPhrase = "t9G2ymCmDsQij7VtYinqrbGCOAtDDA3WiNr";

    protected static boolean isXAXInitted = false;
    protected static boolean needShutdownAfterClass = false;

    public static void initXAX() {
        if (!isXAXInitted) {
            Properties properties = ManualForgingTest.newTestProperties();
            properties.setProperty("xax.isTestnet", "true");
            properties.setProperty("xax.isOffline", "true");
            properties.setProperty("xax.enableFakeForging", "true");
            properties.setProperty("xax.fakeForgingAccount", forgerAccountId);
            properties.setProperty("xax.timeMultiplier", "1");
            properties.setProperty("xax.testnetGuaranteedBalanceConfirmations", "1");
            properties.setProperty("xax.testnetLeasingDelay", "1");
            properties.setProperty("xax.disableProcessTransactionsThread", "true");
            properties.setProperty("xax.deleteFinishedShufflings", "false");
            AbstractForgingTest.init(properties);
            isXAXInitted = true;
        }
    }
    
    @BeforeClass
    public static void init() {
        needShutdownAfterClass = !isXAXInitted;
        initXAX();
        
        XAX.setTime(new Time.CounterTime(XAX.getEpochTime()));
        baseHeight = blockchain.getHeight();
        Logger.logMessage("baseHeight: " + baseHeight);
        FORGY = new Tester(forgerSecretPhrase);
        ALICE = new Tester(aliceSecretPhrase);
        BOB = new Tester(bobSecretPhrase2);
        CHUCK = new Tester(chuckSecretPhrase);
        DAVE = new Tester(daveSecretPhrase);
    }

    @AfterClass
    public static void shutdown() {
        if (needShutdownAfterClass) {
            XAX.shutdown();
        }
    }

    @After
    public void destroy() {
        TransactionProcessorImpl.getInstance().clearUnconfirmedTransactions();
        blockchainProcessor.popOffTo(baseHeight);
        shutdown();
    }

    public static void generateBlock() {
        try {
            blockchainProcessor.generateBlock(forgerSecretPhrase, XAX.getEpochTime());
        } catch (BlockchainProcessor.BlockNotAcceptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public static void generateBlocks(int howMany) {
        for (int i = 0; i < howMany; i++) {
            generateBlock();
        }
    }
}
