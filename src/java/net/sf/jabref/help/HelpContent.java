begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.help
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|help
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|Stack
import|;
end_import

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
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|HyperlinkEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|HyperlinkListener
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
name|GUIGlobals
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
name|JabRef
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
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|HelpContent
specifier|public
class|class
name|HelpContent
extends|extends
name|JTextPane
block|{
DECL|field|log
specifier|static
name|Log
name|log
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|HelpContent
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|pane
name|JScrollPane
name|pane
decl_stmt|;
DECL|field|history
DECL|field|forw
specifier|private
name|Stack
argument_list|<
name|URL
argument_list|>
name|history
decl_stmt|,
name|forw
decl_stmt|;
DECL|field|prefs
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|method|HelpContent (JabRefPreferences prefs_)
specifier|public
name|HelpContent
parameter_list|(
name|JabRefPreferences
name|prefs_
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|pane
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
name|pane
operator|.
name|setDoubleBuffered
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|prefs
operator|=
name|prefs_
expr_stmt|;
name|history
operator|=
operator|new
name|Stack
argument_list|<
name|URL
argument_list|>
argument_list|()
expr_stmt|;
name|forw
operator|=
operator|new
name|Stack
argument_list|<
name|URL
argument_list|>
argument_list|()
expr_stmt|;
name|setEditorKitForContentType
argument_list|(
literal|"text/html"
argument_list|,
operator|new
name|MyEditorKit
argument_list|()
argument_list|)
expr_stmt|;
name|setContentType
argument_list|(
literal|"text/html"
argument_list|)
expr_stmt|;
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// Handles Anchors
specifier|final
name|HyperlinkListener
name|hyperLinkListener
init|=
operator|new
name|HyperlinkListener
argument_list|()
block|{
specifier|public
name|void
name|hyperlinkUpdate
parameter_list|(
specifier|final
name|HyperlinkEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getDescription
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"#"
argument_list|)
condition|)
block|{
name|scrollToReference
argument_list|(
name|e
operator|.
name|getDescription
argument_list|()
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
decl_stmt|;
name|addHyperlinkListener
argument_list|(
name|hyperLinkListener
argument_list|)
expr_stmt|;
block|}
DECL|method|back ()
specifier|public
name|boolean
name|back
parameter_list|()
block|{
if|if
condition|(
operator|!
name|history
operator|.
name|empty
argument_list|()
condition|)
block|{
name|URL
name|prev
init|=
operator|(
name|history
operator|.
name|pop
argument_list|()
operator|)
decl_stmt|;
name|forw
operator|.
name|push
argument_list|(
name|getPage
argument_list|()
argument_list|)
expr_stmt|;
name|setPageOnly
argument_list|(
name|prev
argument_list|)
expr_stmt|;
block|}
return|return
operator|!
name|history
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|forward ()
specifier|public
name|boolean
name|forward
parameter_list|()
block|{
if|if
condition|(
operator|!
name|forw
operator|.
name|empty
argument_list|()
condition|)
block|{
name|URL
name|next
init|=
operator|(
name|forw
operator|.
name|pop
argument_list|()
operator|)
decl_stmt|;
name|history
operator|.
name|push
argument_list|(
name|getPage
argument_list|()
argument_list|)
expr_stmt|;
name|setPageOnly
argument_list|(
name|next
argument_list|)
expr_stmt|;
block|}
return|return
operator|!
name|forw
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|reset ()
specifier|public
name|void
name|reset
parameter_list|()
block|{
name|forw
operator|.
name|removeAllElements
argument_list|()
expr_stmt|;
name|history
operator|.
name|removeAllElements
argument_list|()
expr_stmt|;
block|}
DECL|method|setPage (String filename, Class resourceOwner)
specifier|public
name|void
name|setPage
parameter_list|(
name|String
name|filename
parameter_list|,
name|Class
name|resourceOwner
parameter_list|)
block|{
comment|// Check for anchor
name|int
name|indexOf
init|=
name|filename
operator|.
name|indexOf
argument_list|(
literal|'#'
argument_list|)
decl_stmt|;
name|String
name|file
decl_stmt|;
name|String
name|reference
decl_stmt|;
if|if
condition|(
name|indexOf
operator|!=
operator|-
literal|1
condition|)
block|{
name|file
operator|=
name|filename
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|indexOf
argument_list|)
expr_stmt|;
name|reference
operator|=
name|filename
operator|.
name|substring
argument_list|(
name|indexOf
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|file
operator|=
name|filename
expr_stmt|;
name|reference
operator|=
literal|""
expr_stmt|;
block|}
name|String
name|middle
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"language"
argument_list|)
operator|+
literal|"/"
decl_stmt|;
if|if
condition|(
name|middle
operator|.
name|equals
argument_list|(
literal|"en/"
argument_list|)
condition|)
name|middle
operator|=
literal|""
expr_stmt|;
comment|// english in base help dir.
name|URL
name|old
init|=
name|getPage
argument_list|()
decl_stmt|;
try|try
block|{
comment|// First check in specified language
name|URL
name|resource
init|=
name|resourceOwner
operator|.
name|getResource
argument_list|(
name|GUIGlobals
operator|.
name|helpPre
operator|+
name|middle
operator|+
name|file
argument_list|)
decl_stmt|;
comment|// If not available fallback to english
if|if
condition|(
name|resource
operator|==
literal|null
condition|)
block|{
name|resource
operator|=
name|resourceOwner
operator|.
name|getResource
argument_list|(
name|GUIGlobals
operator|.
name|helpPre
operator|+
name|file
argument_list|)
expr_stmt|;
block|}
comment|// If still not available print a warning
if|if
condition|(
name|resource
operator|==
literal|null
condition|)
block|{
comment|// TODO show warning to user
name|log
operator|.
name|error
argument_list|(
literal|"Could not find html-help for file '"
operator|+
name|file
operator|+
literal|"'."
argument_list|)
expr_stmt|;
return|return;
block|}
name|setPageOnly
argument_list|(
operator|new
name|URL
argument_list|(
name|resource
operator|.
name|toString
argument_list|()
operator|+
literal|"#"
operator|+
name|reference
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|forw
operator|.
name|removeAllElements
argument_list|()
expr_stmt|;
if|if
condition|(
name|old
operator|!=
literal|null
condition|)
name|history
operator|.
name|push
argument_list|(
name|old
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Convenience method for setPage(String) 	 */
DECL|method|setPage (URL url)
specifier|public
name|void
name|setPage
parameter_list|(
name|URL
name|url
parameter_list|)
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|url
operator|.
name|getPath
argument_list|()
argument_list|)
decl_stmt|;
name|setPage
argument_list|(
name|f
operator|.
name|getName
argument_list|()
argument_list|,
name|JabRef
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
DECL|method|setPageOnly (URL url)
specifier|private
name|void
name|setPageOnly
parameter_list|(
name|URL
name|url
parameter_list|)
block|{
try|try
block|{
name|super
operator|.
name|setPage
argument_list|(
name|url
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
if|if
condition|(
name|url
operator|==
literal|null
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Error: Help file not set"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Error: Help file not found '"
operator|+
name|url
operator|.
name|getFile
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|getPane ()
specifier|public
name|JComponent
name|getPane
parameter_list|()
block|{
return|return
name|pane
return|;
block|}
comment|/*public void paintComponent(Graphics g) { 		Graphics2D g2 = (Graphics2D) g; 		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 		super.paintComponent(g2); 	}*/
block|}
end_class

end_unit

