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
import xax.Alias;
import xax.Attachment;
import xax.XAXException;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

import static xax.http.JSONResponses.INCORRECT_ALIAS_OWNER;


public final class DeleteAlias extends CreateTransaction {

    static final DeleteAlias instance = new DeleteAlias();

    private DeleteAlias() {
        super(new APITag[] {APITag.ALIASES, APITag.CREATE_TRANSACTION}, "alias", "aliasName");
    }

    @Override
    protected JSONStreamAware processRequest(final HttpServletRequest req) throws XAXException {
        final Alias alias = ParameterParser.getAlias(req);
        final Account owner = ParameterParser.getSenderAccount(req);

        if (alias.getAccountId() != owner.getId()) {
            return INCORRECT_ALIAS_OWNER;
        }

        final Attachment attachment = new Attachment.MessagingAliasDelete(alias.getAliasName());
        return createTransaction(req, owner, attachment);
    }
}
