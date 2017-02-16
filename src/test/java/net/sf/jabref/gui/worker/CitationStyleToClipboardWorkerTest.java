begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.worker
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|worker
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|DataFlavor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|StringSelection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|exporter
operator|.
name|RtfTransferable
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|HtmlTransferable
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|XmlTransferable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|OS
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
DECL|class|CitationStyleToClipboardWorkerTest
specifier|public
class|class
name|CitationStyleToClipboardWorkerTest
block|{
annotation|@
name|Test
DECL|method|processPreviewText ()
specifier|public
name|void
name|processPreviewText
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|expected
init|=
literal|"Article (Smith2016)Smith, B.; Jones, B.& Williams, J.Taylor, P. (Ed.)Title of the test entry BibTeX Journal, JabRef Publishing, 2016, 34, 45-67 Abstract: This entry describes a test scenario which may be useful in JabRef. By providing a test entry it is possible to see how certain things will look in this graphical BIB-file mananger."
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"Article (Smith2016)Smith, B.; Jones, B.& Williams, J.Taylor, P. (Ed.)Title of the test entry BibTeX Journal, JabRef Publishing, 2016, 34, 45-67 Abstract: This entry describes a test scenario which may be useful in JabRef. By providing a test entry it is possible to see how certain things will look in this graphical BIB-file mananger."
decl_stmt|;
name|String
name|citation
init|=
literal|"Article (Smith2016)"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"Smith, B.; Jones, B.&amp; Williams, J."
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"Taylor, P. (Ed.)"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"Title of the test entry "
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"BibTeX Journal, JabRef Publishing, 2016, 34, 45-67 "
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"Abstract:  This entry describes a test scenario which may be useful in JabRef. By providing a test entry it is possible to see how certain things will look in this graphical BIB-file mananger. "
decl_stmt|;
name|HtmlTransferable
name|HtmlTransferable
init|=
name|CitationStyleToClipboardWorker
operator|.
name|processPreview
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|citation
argument_list|,
name|citation
argument_list|)
argument_list|)
decl_stmt|;
name|Object
name|actual
init|=
name|HtmlTransferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|processPreviewHtml ()
specifier|public
name|void
name|processPreviewHtml
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|expected
init|=
literal|"<font face=\"sans-serif\"><b><i>Article</i><a name=\"Smith2016\"> (Smith2016)</a></b><br>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" Smith, B.; Jones, B.&amp; Williams, J.<BR>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" Taylor, P.<i>(Ed.)</i><BR>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" Title of the test entry<BR>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<em>BibTeX Journal,</em>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<em>JabRef Publishing,</em>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<b>2016</b><i>, 34</i>, 45-67 "
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<BR><BR><b>Abstract:</b> This entry describes a test scenario which may be useful in JabRef. By providing a test entry it is possible to see how certain things will look in this graphical BIB-file mananger. "
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</dd>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<p></p></font>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<br>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<font face=\"sans-serif\"><b><i>Article</i><a name=\"Smith2016\"> (Smith2016)</a></b><br>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" Smith, B.; Jones, B.&amp; Williams, J.<BR>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" Taylor, P.<i>(Ed.)</i><BR>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" Title of the test entry<BR>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<em>BibTeX Journal,</em>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<em>JabRef Publishing,</em>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<b>2016</b><i>, 34</i>, 45-67 "
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<BR><BR><b>Abstract:</b> This entry describes a test scenario which may be useful in JabRef. By providing a test entry it is possible to see how certain things will look in this graphical BIB-file mananger. "
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</dd>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<p></p></font>"
decl_stmt|;
name|String
name|citation
init|=
literal|"<font face=\"sans-serif\"><b><i>Article</i><a name=\"Smith2016\"> (Smith2016)</a></b><br>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" Smith, B.; Jones, B.&amp; Williams, J.<BR>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" Taylor, P.<i>(Ed.)</i><BR>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" Title of the test entry<BR>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<em>BibTeX Journal,</em>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<em>JabRef Publishing,</em>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<b>2016</b><i>, 34</i>, 45-67 "
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<BR><BR><b>Abstract:</b> This entry describes a test scenario which may be useful in JabRef. By providing a test entry it is possible to see how certain things will look in this graphical BIB-file mananger. "
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</dd>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<p></p></font>"
decl_stmt|;
name|HtmlTransferable
name|transferable
init|=
name|CitationStyleToClipboardWorker
operator|.
name|processPreview
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|citation
argument_list|,
name|citation
argument_list|)
argument_list|)
decl_stmt|;
name|Object
name|actual
init|=
name|transferable
operator|.
name|getTransferData
argument_list|(
name|HtmlTransferable
operator|.
name|HTML_FLAVOR
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|processText ()
specifier|public
name|void
name|processText
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|expected
init|=
literal|"[1]B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â BibTeX Journal, vol. 34, no. 3, pp. 45â67, Jul. 2016."
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"[1]B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â BibTeX Journal, vol. 34, no. 3, pp. 45â67, Jul. 2016."
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|String
name|citation
init|=
literal|"[1]B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â BibTeX Journal, vol. 34, no. 3, pp. 45â67, Jul. 2016."
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|StringSelection
name|textTransferable
init|=
name|CitationStyleToClipboardWorker
operator|.
name|processText
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|citation
argument_list|,
name|citation
argument_list|)
argument_list|)
decl_stmt|;
name|Object
name|actual
init|=
name|textTransferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|processRtf ()
specifier|public
name|void
name|processRtf
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|expected
init|=
literal|"{\\rtf"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"[1]\\tab B. Smith, B. Jones, and J. Williams, \\uc0\\u8220{}Title of the test entry,\\uc0\\u8221{} {\\i{}BibTeX Journal}, vol. 34, no. 3, pp. 45\\uc0\\u8211{}67, Jul. 2016."
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"\\line"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"[1]\\tab B. Smith, B. Jones, and J. Williams, \\uc0\\u8220{}Title of the test entry,\\uc0\\u8221{} {\\i{}BibTeX Journal}, vol. 34, no. 3, pp. 45\\uc0\\u8211{}67, Jul. 2016."
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"}"
decl_stmt|;
name|String
name|citation
init|=
literal|"[1]\\tab B. Smith, B. Jones, and J. Williams, \\uc0\\u8220{}Title of the test entry,\\uc0\\u8221{} {\\i{}BibTeX Journal}, vol. 34, no. 3, pp. 45\\uc0\\u8211{}67, Jul. 2016."
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|RtfTransferable
name|rtfTransferable
init|=
name|CitationStyleToClipboardWorker
operator|.
name|processRtf
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|citation
argument_list|,
name|citation
argument_list|)
argument_list|)
decl_stmt|;
name|Object
name|actual
init|=
name|rtfTransferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|processXslFo ()
specifier|public
name|void
name|processXslFo
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|expected
init|=
literal|"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:layout-master-set>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:simple-page-master master-name=\"citations\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:region-body/>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:simple-page-master>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:layout-master-set>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:page-sequence master-reference=\"citations\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:flow flow-name=\"xsl-region-body\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:block id=\"Smith2016\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table table-layout=\"fixed\" width=\"100%\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-column column-number=\"1\" column-width=\"2.5em\"/>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-column column-number=\"2\" column-width=\"proportional-column-width(1)\"/>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-row>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:block>[1]</fo:block>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:block>B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â<fo:inline font-style=\"italic\">BibTeX Journal</fo:inline>, vol. 34, no. 3, pp. 45â67, Jul. 2016.</fo:block>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-row>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:block>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:block id=\"Smith2016\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table table-layout=\"fixed\" width=\"100%\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-column column-number=\"1\" column-width=\"2.5em\"/>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-column column-number=\"2\" column-width=\"proportional-column-width(1)\"/>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-row>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:block>[1]</fo:block>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:block>B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â<fo:inline font-style=\"italic\">BibTeX Journal</fo:inline>, vol. 34, no. 3, pp. 45â67, Jul. 2016.</fo:block>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-row>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:block>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:flow>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:page-sequence>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:root>"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|String
name|citation
init|=
literal|"<fo:block id=\"Smith2016\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table table-layout=\"fixed\" width=\"100%\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-column column-number=\"1\" column-width=\"2.5em\"/>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-column column-number=\"2\" column-width=\"proportional-column-width(1)\"/>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-row>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:block>[1]</fo:block>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<fo:block>B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â<fo:inline font-style=\"italic\">BibTeX Journal</fo:inline>, vol. 34, no. 3, pp. 45â67, Jul. 2016.</fo:block>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-cell>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-row>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table-body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:table>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</fo:block>"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|XmlTransferable
name|xmlTransferable
init|=
name|CitationStyleToClipboardWorker
operator|.
name|processXslFo
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|citation
argument_list|,
name|citation
argument_list|)
argument_list|)
decl_stmt|;
name|Object
name|actual
init|=
name|xmlTransferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|processHtmlAsHtml ()
specifier|public
name|void
name|processHtmlAsHtml
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|expected
init|=
literal|"<!DOCTYPE html>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<html>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<head>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<meta charset=\"utf-8\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</head>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-entry\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-left-margin\">[1]</div><div class=\"csl-right-inline\">B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â<i>BibTeX Journal</i>, vol. 34, no. 3, pp. 45â67, Jul. 2016.</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<br>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-entry\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-left-margin\">[1]</div><div class=\"csl-right-inline\">B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â<i>BibTeX Journal</i>, vol. 34, no. 3, pp. 45â67, Jul. 2016.</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</body>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</html>"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|String
name|citation
init|=
literal|"<div class=\"csl-entry\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-left-margin\">[1]</div><div class=\"csl-right-inline\">B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â<i>BibTeX Journal</i>, vol. 34, no. 3, pp. 45â67, Jul. 2016.</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</div>"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|HtmlTransferable
name|htmlTransferable
init|=
name|CitationStyleToClipboardWorker
operator|.
name|processHtml
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|citation
argument_list|,
name|citation
argument_list|)
argument_list|)
decl_stmt|;
name|Object
name|actual
init|=
name|htmlTransferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|allHtmlFlavor
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|processHtmlAsText ()
specifier|public
name|void
name|processHtmlAsText
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|expected
init|=
literal|"[1] B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â BibTeX Journal , vol. 34, no. 3, pp. 45â67, Jul. 2016."
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"[1] B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â BibTeX Journal , vol. 34, no. 3, pp. 45â67, Jul. 2016."
decl_stmt|;
name|String
name|citation
init|=
literal|"<div class=\"csl-entry\">"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"<div class=\"csl-left-margin\">[1]</div><div class=\"csl-right-inline\">B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â<i>BibTeX Journal</i>, vol. 34, no. 3, pp. 45â67, Jul. 2016.</div>"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"</div>"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
name|HtmlTransferable
name|htmlTransferable
init|=
name|CitationStyleToClipboardWorker
operator|.
name|processHtml
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|citation
argument_list|,
name|citation
argument_list|)
argument_list|)
decl_stmt|;
name|Object
name|actual
init|=
name|htmlTransferable
operator|.
name|getTransferData
argument_list|(
name|DataFlavor
operator|.
name|stringFlavor
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

