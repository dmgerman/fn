begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|export
operator|.
name|layout
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|RenderingHints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics2D
import|;
end_import

begin_class
DECL|class|PreviewPanel
specifier|public
class|class
name|PreviewPanel
extends|extends
name|JEditorPane
block|{
DECL|field|CONTENT_TYPE
specifier|public
name|String
name|CONTENT_TYPE
init|=
literal|"text/html"
decl_stmt|,
DECL|field|LAYOUT_FILE
name|LAYOUT_FILE
init|=
literal|"simplehtml"
decl_stmt|;
DECL|field|entry
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|layout
name|Layout
name|layout
decl_stmt|;
DECL|field|prefix
DECL|field|postfix
name|String
name|prefix
init|=
literal|""
decl_stmt|,
name|postfix
init|=
literal|""
decl_stmt|;
DECL|field|DIM
name|Dimension
name|DIM
init|=
operator|new
name|Dimension
argument_list|(
literal|650
argument_list|,
literal|110
argument_list|)
decl_stmt|;
DECL|field|layouts
name|HashMap
name|layouts
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|method|PreviewPanel (BibtexEntry be)
specifier|public
name|PreviewPanel
parameter_list|(
name|BibtexEntry
name|be
parameter_list|)
block|{
name|entry
operator|=
name|be
expr_stmt|;
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setContentType
argument_list|(
name|CONTENT_TYPE
argument_list|)
expr_stmt|;
try|try
block|{
name|readLayout
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{     }
name|update
argument_list|()
expr_stmt|;
block|}
DECL|method|readLayout ()
specifier|public
name|void
name|readLayout
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|entryType
init|=
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
name|layouts
operator|.
name|get
argument_list|(
name|entryType
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|layout
operator|=
operator|(
name|Layout
operator|)
name|layouts
operator|.
name|get
argument_list|(
name|entryType
argument_list|)
expr_stmt|;
return|return;
block|}
name|LayoutHelper
name|layoutHelper
init|=
literal|null
decl_stmt|;
name|URL
name|reso
init|=
name|JabRefFrame
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|Globals
operator|.
name|LAYOUT_PREFIX
operator|+
name|LAYOUT_FILE
operator|+
literal|"."
operator|+
name|entryType
operator|+
literal|".layout"
argument_list|)
decl_stmt|;
comment|//Util.pr(Globals.LAYOUT_PREFIX+LAYOUT_FILE+"."+entryType+".layout");
try|try
block|{
if|if
condition|(
name|reso
operator|==
literal|null
condition|)
name|reso
operator|=
name|JabRefFrame
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|Globals
operator|.
name|LAYOUT_PREFIX
operator|+
name|LAYOUT_FILE
operator|+
literal|".layout"
argument_list|)
expr_stmt|;
name|layoutHelper
operator|=
operator|new
name|LayoutHelper
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|reso
operator|.
name|openStream
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{       }
name|layout
operator|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|()
expr_stmt|;
name|layouts
operator|.
name|put
argument_list|(
name|entryType
argument_list|,
name|layout
argument_list|)
expr_stmt|;
name|reso
operator|=
name|JabRefFrame
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|Globals
operator|.
name|LAYOUT_PREFIX
operator|+
name|LAYOUT_FILE
operator|+
literal|".begin.layout"
argument_list|)
expr_stmt|;
name|StringWriter
name|stw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|InputStreamReader
name|reader
decl_stmt|;
name|int
name|c
decl_stmt|;
if|if
condition|(
name|reso
operator|!=
literal|null
condition|)
block|{
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
name|reso
operator|.
name|openStream
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|stw
operator|.
name|write
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
name|prefix
operator|=
name|stw
operator|.
name|toString
argument_list|()
expr_stmt|;
name|reso
operator|=
name|JabRefFrame
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|Globals
operator|.
name|LAYOUT_PREFIX
operator|+
name|LAYOUT_FILE
operator|+
literal|".end.layout"
argument_list|)
expr_stmt|;
name|stw
operator|=
operator|new
name|StringWriter
argument_list|()
expr_stmt|;
if|if
condition|(
name|reso
operator|!=
literal|null
condition|)
block|{
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
name|reso
operator|.
name|openStream
argument_list|()
argument_list|)
expr_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|stw
operator|.
name|write
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
name|postfix
operator|=
name|stw
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
DECL|method|setEntry (BibtexEntry newEntry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|BibtexEntry
name|newEntry
parameter_list|)
block|{
name|entry
operator|=
name|newEntry
expr_stmt|;
try|try
block|{
name|readLayout
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{     }
name|update
argument_list|()
expr_stmt|;
block|}
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
comment|//StringBuffer sb = new StringBuffer(prefix);
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|layout
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
comment|//sb.append(postfix);
name|setText
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|//Util.pr(sb.toString());
block|}
DECL|method|getPreferredSize ()
specifier|public
name|Dimension
name|getPreferredSize
parameter_list|()
block|{
return|return
name|DIM
return|;
block|}
DECL|method|getMinimumSize ()
specifier|public
name|Dimension
name|getMinimumSize
parameter_list|()
block|{
return|return
name|DIM
return|;
block|}
DECL|method|paintComponent (Graphics g)
specifier|public
name|void
name|paintComponent
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
name|Graphics2D
name|g2
init|=
operator|(
name|Graphics2D
operator|)
name|g
decl_stmt|;
name|g2
operator|.
name|setRenderingHint
argument_list|(
name|RenderingHints
operator|.
name|KEY_ANTIALIASING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_ANTIALIAS_ON
argument_list|)
expr_stmt|;
name|g2
operator|.
name|setRenderingHint
argument_list|(
name|RenderingHints
operator|.
name|KEY_RENDERING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_RENDER_QUALITY
argument_list|)
expr_stmt|;
name|super
operator|.
name|paintComponent
argument_list|(
name|g2
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

