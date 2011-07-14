/*
 * RHQ Management Platform
 * Copyright (C) 2010 Red Hat, Inc.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package org.rhq.enterprise.server.search;

/**
 * An exception to be ignored by the PagedListDataModel when fetching the page in a guarded fashion,
 * so that the first time an exception occurs it is allowed to bubble up to the surface.  This exception
 * is generated by the antlr-based parser upon capturing a RecognitionException from the lexer.
 *
 * @author Joseph Marques
 */
public class SearchExpressionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SearchExpressionException() {
    }

    public SearchExpressionException(String message) {
        super(message);
    }

    public SearchExpressionException(Throwable cause) {
        super(cause);
    }

    public SearchExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

}
