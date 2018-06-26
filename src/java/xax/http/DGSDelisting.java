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

import static xax.http.JSONResponses.UNKNOWN_GOODS;

public final class DGSDelisting extends CreateTransaction {

    static final DGSDelisting instance = new DGSDelisting();

    private DGSDelisting() {
        super(new APITag[] {APITag.DGS, APITag.CREATE_TRANSACTION}, "goods");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws XAXException {
        Account account = ParameterParser.getSenderAccount(req);
        DigitalGoodsStore.Goods goods = ParameterParser.getGoods(req);
        if (goods.isDelisted() || goods.getSellerId() != account.getId()) {
            return UNKNOWN_GOODS;
        }
        Attachment attachment = new Attachment.DigitalGoodsDelisting(goods.getId());
        return createTransaction(req, account, attachment);
    }

}
