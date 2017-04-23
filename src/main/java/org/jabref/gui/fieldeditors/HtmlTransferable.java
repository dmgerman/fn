begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
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
name|Transferable
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
name|UnsupportedFlavorException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|HtmlToUnicodeFormatter
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

begin_comment
comment|/**  Based on http://newsgroups.derkeiler.com/Archive/De/de.comp.lang.java/2010-04/msg00203.html  */
end_comment

begin_class
DECL|class|HtmlTransferable
specifier|public
class|class
name|HtmlTransferable
implements|implements
name|Transferable
block|{
DECL|field|HTML_FLAVOR
specifier|public
specifier|static
specifier|final
name|DataFlavor
name|HTML_FLAVOR
init|=
operator|new
name|DataFlavor
argument_list|(
literal|"text/html;charset=utf-8;class=java.lang.String"
argument_list|,
literal|"HTML Format"
argument_list|)
decl_stmt|;
DECL|field|TEXT_FLAVOR
specifier|public
specifier|static
specifier|final
name|DataFlavor
name|TEXT_FLAVOR
init|=
name|DataFlavor
operator|.
name|stringFlavor
decl_stmt|;
DECL|field|ALL_FLAVORS
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|DataFlavor
argument_list|>
name|ALL_FLAVORS
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|HTML_FLAVOR
argument_list|,
name|DataFlavor
operator|.
name|allHtmlFlavor
argument_list|,
name|TEXT_FLAVOR
argument_list|)
decl_stmt|;
DECL|field|HTML_NEWLINE
specifier|private
specifier|static
specifier|final
name|Pattern
name|HTML_NEWLINE
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|" ?<br>|<BR>"
argument_list|)
decl_stmt|;
DECL|field|REMOVE_HTML
specifier|private
specifier|static
specifier|final
name|Pattern
name|REMOVE_HTML
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<(?!br)(?!BR).*?>"
argument_list|)
decl_stmt|;
DECL|field|WHITESPACE_AROUND_NEWLINE
specifier|private
specifier|static
specifier|final
name|Pattern
name|WHITESPACE_AROUND_NEWLINE
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(?m)^\\s+|\\v+"
argument_list|)
decl_stmt|;
DECL|field|DOUBLE_WHITESPACES
specifier|private
specifier|static
specifier|final
name|Pattern
name|DOUBLE_WHITESPACES
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[ ]{2,}"
argument_list|)
decl_stmt|;
DECL|field|htmlText
specifier|private
specifier|final
name|String
name|htmlText
decl_stmt|;
DECL|field|plainText
specifier|private
specifier|final
name|String
name|plainText
decl_stmt|;
DECL|method|HtmlTransferable (String html)
specifier|public
name|HtmlTransferable
parameter_list|(
name|String
name|html
parameter_list|)
block|{
name|this
operator|.
name|htmlText
operator|=
name|html
expr_stmt|;
comment|// convert html to text by stripping out HTML
name|String
name|plain
init|=
name|html
decl_stmt|;
name|plain
operator|=
name|REMOVE_HTML
operator|.
name|matcher
argument_list|(
name|plain
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|plain
operator|=
name|WHITESPACE_AROUND_NEWLINE
operator|.
name|matcher
argument_list|(
name|plain
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|plain
operator|=
name|DOUBLE_WHITESPACES
operator|.
name|matcher
argument_list|(
name|plain
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
name|plain
operator|=
name|HTML_NEWLINE
operator|.
name|matcher
argument_list|(
name|plain
argument_list|)
operator|.
name|replaceAll
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
comment|// replace all HTML codes such as&amp;
name|plain
operator|=
operator|new
name|HtmlToUnicodeFormatter
argument_list|()
operator|.
name|format
argument_list|(
name|plain
argument_list|)
expr_stmt|;
name|this
operator|.
name|plainText
operator|=
name|plain
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
DECL|method|HtmlTransferable (String htmlText, String plainText)
specifier|public
name|HtmlTransferable
parameter_list|(
name|String
name|htmlText
parameter_list|,
name|String
name|plainText
parameter_list|)
block|{
name|this
operator|.
name|htmlText
operator|=
name|htmlText
expr_stmt|;
name|this
operator|.
name|plainText
operator|=
name|plainText
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getTransferDataFlavors ()
specifier|public
name|DataFlavor
index|[]
name|getTransferDataFlavors
parameter_list|()
block|{
return|return
name|ALL_FLAVORS
operator|.
name|toArray
argument_list|(
operator|new
name|DataFlavor
index|[
name|ALL_FLAVORS
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|isDataFlavorSupported (DataFlavor flavor)
specifier|public
name|boolean
name|isDataFlavorSupported
parameter_list|(
name|DataFlavor
name|flavor
parameter_list|)
block|{
return|return
name|ALL_FLAVORS
operator|.
name|parallelStream
argument_list|()
operator|.
name|anyMatch
argument_list|(
name|flavor
operator|::
name|equals
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getTransferData (DataFlavor flavor)
specifier|public
name|Object
name|getTransferData
parameter_list|(
name|DataFlavor
name|flavor
parameter_list|)
throws|throws
name|UnsupportedFlavorException
throws|,
name|IOException
block|{
if|if
condition|(
name|flavor
operator|.
name|equals
argument_list|(
name|HTML_FLAVOR
argument_list|)
operator|||
name|flavor
operator|.
name|equals
argument_list|(
name|DataFlavor
operator|.
name|allHtmlFlavor
argument_list|)
condition|)
block|{
return|return
name|htmlText
return|;
block|}
elseif|else
if|if
condition|(
name|flavor
operator|.
name|equals
argument_list|(
name|TEXT_FLAVOR
argument_list|)
condition|)
block|{
return|return
name|plainText
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|UnsupportedFlavorException
argument_list|(
name|flavor
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

