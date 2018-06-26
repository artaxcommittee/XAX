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

package xax.http;

import xax.Account;
import xax.Attachment;
import xax.DigitalGoodsStore;
import xax.XAXException;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

import static xax.http.JSONResponses.GOODS_NOT_DELIVERED;
import static xax.http.JSONResponses.INCORRECT_PURCHASE;

public final class DGSFeedback extends CreateTransaction {

    static final DGSFeedback instance = new DGSFeedback();

    private DGSFeedback() {
        super(new APITag[] {APITag.DGS, APITag.CREATE_TRANSACTION},
                "purchase");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws XAXException {

        DigitalGoodsStore.Purchase purchase = ParameterParser.getPurchase(req);

        Account buyerAccount = ParameterParser.getSenderAccount(req);
        if (buyerAccount.getId() != purchase.getBuyerId()) {
            return INCORRECT_PURCHASE;
        }
        if (purchase.getEncryptedGoods() == null) {
            return GOODS_NOT_DELIVERED;
        }

        Account sellerAccount = Account.getAccount(purchase.getSellerId());
        Attachment attachment = new Attachment.DigitalGoodsFeedback(purchase.getId());
        return createTransaction(req, buyerAccount, sellerAccount.getId(), 0, attachment);
    }

}
