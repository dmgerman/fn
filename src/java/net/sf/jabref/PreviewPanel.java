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
decl_stmt|;
comment|//LAYOUT_FILE = "simplehtml";
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
DECL|field|layoutFile
name|String
name|layoutFile
decl_stmt|;
DECL|field|sp
name|JScrollPane
name|sp
decl_stmt|;
DECL|method|PreviewPanel (BibtexEntry be, String layoutFile)
specifier|public
name|PreviewPanel
parameter_list|(
name|BibtexEntry
name|be
parameter_list|,
name|String
name|layoutFile
parameter_list|)
block|{
name|entry
operator|=
name|be
expr_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|this
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
expr_stmt|;
comment|//Util.pr(layoutFile);
name|init
argument_list|()
expr_stmt|;
name|this
operator|.
name|layoutFile
operator|=
name|layoutFile
expr_stmt|;
try|try
block|{
name|readLayout
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|update
argument_list|()
expr_stmt|;
block|}
DECL|method|PreviewPanel (String layoutFile)
specifier|public
name|PreviewPanel
parameter_list|(
name|String
name|layoutFile
parameter_list|)
block|{
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|this
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
expr_stmt|;
name|this
operator|.
name|layoutFile
operator|=
name|layoutFile
expr_stmt|;
name|init
argument_list|()
expr_stmt|;
comment|//setText("<HTML></HTML>");
block|}
DECL|method|init ()
specifier|private
name|void
name|init
parameter_list|()
block|{
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
comment|//setSize(100, 100);
block|}
DECL|method|getPane ()
specifier|public
name|JScrollPane
name|getPane
parameter_list|()
block|{
return|return
name|sp
return|;
block|}
DECL|method|readLayout (String layoutFormat)
specifier|public
name|void
name|readLayout
parameter_list|(
name|String
name|layoutFormat
parameter_list|)
throws|throws
name|Exception
block|{
name|layoutFile
operator|=
name|layoutFormat
expr_stmt|;
name|readLayout
argument_list|()
expr_stmt|;
block|}
DECL|method|readLayout ()
specifier|public
name|void
name|readLayout
parameter_list|()
throws|throws
name|Exception
block|{
name|LayoutHelper
name|layoutHelper
init|=
literal|null
decl_stmt|;
name|StringReader
name|sr
init|=
operator|new
name|StringReader
argument_list|(
name|layoutFile
operator|.
name|replaceAll
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
name|layoutHelper
operator|=
operator|new
name|LayoutHelper
argument_list|(
name|sr
argument_list|)
expr_stmt|;
name|layout
operator|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|(
name|Globals
operator|.
name|FORMATTER_PACKAGE
argument_list|)
expr_stmt|;
comment|/*String entryType = entry.getType().getName().toLowerCase();       if (layouts.get(entryType) != null) { 	  layout = (Layout)layouts.get(entryType); 	  return;       }*/
comment|//URL reso = JabRefFrame.class.getResource
comment|//  (Globals.LAYOUT_PREFIX+layoutFile+"."+entryType+".layout");
comment|//Util.pr(Globals.LAYOUT_PREFIX+LAYOUT_FILE+"."+entryType+".layout");
comment|/*try {         if (reso == null)           reso = JabRefFrame.class.getResource(Globals.LAYOUT_PREFIX+layoutFile+".layout");         layoutHelper = new LayoutHelper(new InputStreamReader(reso.openStream()));       }       catch (IOException ex) {       }*/
comment|//layouts.put(entryType, layout);
comment|/*reso = JabRefFrame.class.getResource         (Globals.LAYOUT_PREFIX+layoutFile+".begin.layout");     StringWriter stw = new StringWriter();     InputStreamReader reader;     int c;     if (reso != null) {       reader = new InputStreamReader(reso.openStream());       while ((c = reader.read()) != -1) {         stw.write((char)c);       }       reader.close();     }     prefix = stw.toString();      reso = JabRefFrame.class.getResource         (Globals.LAYOUT_PREFIX+layoutFile+".end.layout");     stw = new StringWriter();     if (reso != null) {       reader = new InputStreamReader(reso.openStream());       while ((c = reader.read()) != -1) {         stw.write((char)c);       }       reader.close();     }     postfix = stw.toString(); */
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
comment|//Util.pr("en");
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
name|Exception
name|ex
parameter_list|)
block|{     }
name|update
argument_list|()
expr_stmt|;
comment|//Util.pr("to");
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
name|invalidate
argument_list|()
expr_stmt|;
name|revalidate
argument_list|()
expr_stmt|;
comment|// Scroll to top:
specifier|final
name|JScrollBar
name|bar
init|=
name|sp
operator|.
name|getVerticalScrollBar
argument_list|()
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|bar
operator|.
name|setValue
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|//Util.pr(sb.toString());
comment|//revalidate();
comment|//Util.pr(""+getPreferredSize()+"\t"+getMinimumSize());
block|}
DECL|method|hasEntry ()
specifier|public
name|boolean
name|hasEntry
parameter_list|()
block|{
return|return
operator|(
name|entry
operator|!=
literal|null
operator|)
return|;
block|}
DECL|method|getPreferredScrollableViewportSize ()
specifier|public
name|Dimension
name|getPreferredScrollableViewportSize
parameter_list|()
block|{
return|return
name|getPreferredSize
argument_list|()
return|;
block|}
comment|/*  public Dimension getPreferredSize() {     Util.pr(""+super.getPreferredSize());     return super.getPreferredSize();   }*/
comment|/*public Dimension getMinimumSize() { return DIM; }*/
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
name|VALUE_ANTIALIAS_OFF
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

