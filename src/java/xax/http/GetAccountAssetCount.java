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
import xax.XAXException;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class GetAccountAssetCount extends APIServlet.APIRequestHandler {

    static final GetAccountAssetCount instance = new GetAccountAssetCount();

    private GetAccountAssetCount() {
        super(new APITag[] {APITag.ACCOUNTS, APITag.AE}, "account", "height");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws XAXException {

        long accountId = ParameterParser.getAccountId(req, true);
        int height = ParameterParser.getHeight(req);

        JSONObject response = new JSONObject();
        response.put("numberOfAssets", Account.getAccountAssetCount(accountId, height));
        return response;
    }

}
