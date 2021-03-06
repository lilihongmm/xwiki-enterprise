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
package org.xwiki.test.selenium;

import junit.framework.Test;

import org.openqa.selenium.By;
import org.xwiki.test.selenium.framework.AbstractXWikiTestCase;
import org.xwiki.test.selenium.framework.FlamingoSkinExecutor;
import org.xwiki.test.selenium.framework.XWikiTestSuite;

/**
 * Verify the document extra feature of XWiki.
 * 
 * @version $Id$
 */
public class DocExtraTest extends AbstractXWikiTestCase
{
    public static Test suite()
    {
        XWikiTestSuite suite = new XWikiTestSuite("Verify the document extra feature of XWiki");
        suite.addTestSuite(DocExtraTest.class, FlamingoSkinExecutor.class);
        return suite;
    }

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        loginAsAdmin();
    }

    /**
     * Test document extras presence after a click on the corresponding tabs.
     */
    public void testDocExtraLoadingFromTabClicks()
    {
        open("Main", "WebHome");

        clickLinkWithXPath("//a[@id='Attachmentslink']", false);
        waitForDocExtraPaneActive("attachments");

        clickLinkWithXPath("//a[@id='Historylink']", false);
        waitForDocExtraPaneActive("history");

        clickLinkWithXPath("//a[@id='Informationlink']", false);
        waitForDocExtraPaneActive("information");

        clickLinkWithXPath("//a[@id='Commentslink']", false);
        waitForDocExtraPaneActive("comments");
    }

    /**
     * Test document extras presence after pressing the corresponding keyboard shortcuts. This test also verify that the
     * browser scrolls to the bottom of the page.
     * 
     * @throws InterruptedException if selenium fails to simulate keyboard shortcut.
     */
    public void testDocExtraLoadingFromKeyboardShortcuts() throws InterruptedException
    {
        open("Main", "WebHome");

        getSkinExecutor().pressKeyboardShortcut("a", false, false, false);
        waitForDocExtraPaneActive("attachments");
        assertDocExtraPaneInView("attachments");
        scrollToPageTop();

        getSkinExecutor().pressKeyboardShortcut("h", false, false, false);
        waitForDocExtraPaneActive("history");
        assertDocExtraPaneInView("history");
        scrollToPageTop();

        getSkinExecutor().pressKeyboardShortcut("i", false, false, false);
        waitForDocExtraPaneActive("information");
        assertDocExtraPaneInView("information");
        scrollToPageTop();

        getSkinExecutor().pressKeyboardShortcut("c", false, false, false);
        waitForDocExtraPaneActive("comments");
        assertDocExtraPaneInView("comments");
    }

    /**
     * Test document extra presence when the user arrives from an URL with anchor. This test also verify that the
     * browser scrolls to the bottom of the page.
     */
    public void testDocExtraLoadingFromURLAnchor()
    {
        // We have to load a different page first since opening the same page with a new anchor doesn't call
        // our functions (on purpose)
        open("Main", "ThisPageDoesNotExist");
        open("Main", "WebHome#Attachments");
        waitForDocExtraPaneActive("attachments");
        assertDocExtraPaneInView("attachments");

        open("Main", "ThisPageDoesNotExist");
        open("Main", "WebHome#History");
        waitForDocExtraPaneActive("history");
        assertDocExtraPaneInView("history");

        open("Main", "ThisPageDoesNotExist");
        open("Main", "WebHome#Information");
        waitForDocExtraPaneActive("information");
        assertDocExtraPaneInView("information");

        open("Main", "ThisPageDoesNotExist");
        open("Main", "WebHome#Comments");
        waitForDocExtraPaneActive("comments");
        assertDocExtraPaneInView("comments");
    }

    /**
     * Test document extra presence after clicks on links directing to the extra tabs (top menu for Toucan skin for
     * example and shortcuts for Colibri skin for example). This test also verify that the browser scrolls to the bottom
     * of the page.
     */
    public void testDocExtraLoadingFromLinks()
    {
        open("Main", "WebHome");

        clickShowAttachments();
        waitForDocExtraPaneActive("attachments");
        assertDocExtraPaneInView("attachments");
        scrollToPageTop();

        clickShowHistory();
        waitForDocExtraPaneActive("history");
        assertDocExtraPaneInView("history");
        scrollToPageTop();

        clickShowInformation();
        waitForDocExtraPaneActive("information");
        assertDocExtraPaneInView("information");
        scrollToPageTop();

        clickShowComments();
        waitForDocExtraPaneActive("comments");
        assertDocExtraPaneInView("comments");
    }

    /**
     * @param paneId valid values: "history", "comments", etc
     */
    private void waitForDocExtraPaneActive(String paneId)
    {
        waitForElement(paneId + "content");
    }

    private void scrollToPageTop()
    {
        getSelenium().getEval("window.scroll(0,0);");
    }

    private void assertDocExtraPaneInView(String paneId)
    {
        String paneContentId = String.format("%scontent", paneId);
        assertElementInView(By.id(paneContentId));
    }
}
