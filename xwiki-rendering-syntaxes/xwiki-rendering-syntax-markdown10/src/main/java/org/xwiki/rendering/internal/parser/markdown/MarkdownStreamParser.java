/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.internal.parser.markdown;

import java.io.Reader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.xwiki.component.annotation.Component;
import org.xwiki.rendering.listener.Listener;
import org.xwiki.rendering.parser.ParseException;
import org.xwiki.rendering.syntax.Syntax;

/**
 * Markdown Streaming Parser.
 *
 * @version $Id$
 * @since 4.5M1
 */
@Component
@Named("markdown/1.0")
@Singleton
public class MarkdownStreamParser extends AbstractMarkdownStreamParser
{
    /**
     * Used to convert Pegdown nodes into XWiki Rendering events.
     */
    @Inject
    private PegdownVisitor pegdownVisitor;

    @Override
    public Syntax getSyntax()
    {
        return Syntax.MARKDOWN_1_0;
    }

    @Override
    protected PegdownVisitor getPegdownVisitor()
    {
        return this.pegdownVisitor;
    }

    @Override
    public void parse(Reader source, Listener listener) throws ParseException
    {
        // The Pegdown processor is not thread safe, thus we need one per thread at least.
        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL & ~Extensions.HARDWRAPS);

        parse(processor, source, listener);
    }
}
