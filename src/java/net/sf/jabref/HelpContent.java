begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

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
name|java
operator|.
name|awt
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
name|event
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
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
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
name|IOException
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
name|event
operator|.
name|HyperlinkListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_class
DECL|class|HelpContent
specifier|public
class|class
name|HelpContent
extends|extends
name|JEditorPane
block|{
DECL|field|pane
name|JScrollPane
name|pane
decl_stmt|;
DECL|field|history
DECL|field|forw
specifier|private
name|Stack
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
name|prefs
operator|=
name|prefs_
expr_stmt|;
name|history
operator|=
operator|new
name|Stack
argument_list|()
expr_stmt|;
name|forw
operator|=
operator|new
name|Stack
argument_list|()
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
call|(
name|URL
call|)
argument_list|(
name|history
operator|.
name|pop
argument_list|()
argument_list|)
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
comment|//System.out.println("HelpContent: "+history.empty());
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
call|(
name|URL
call|)
argument_list|(
name|forw
operator|.
name|pop
argument_list|()
argument_list|)
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
DECL|method|setPage (URL url)
specifier|public
name|void
name|setPage
parameter_list|(
name|URL
name|url
parameter_list|)
block|{
name|String
name|lang
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"language"
argument_list|)
decl_stmt|;
comment|//	if (!url.getPath
name|URL
name|old
init|=
name|getPage
argument_list|()
decl_stmt|;
comment|//System.out.println(url.toString());
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
name|File
name|directory
init|=
operator|new
name|File
argument_list|(
name|f
operator|.
name|getParent
argument_list|()
argument_list|)
decl_stmt|;
comment|/* 	// Search  	File[] listing = directory.listFiles(); 	for (int i=0; i<listing.length(); i++) { 	    if ( 	} 	*/
name|File
name|translatedFile
init|=
operator|new
name|File
argument_list|(
name|directory
operator|.
name|getPath
argument_list|()
operator|+
literal|"/"
operator|+
name|lang
operator|+
literal|"/"
operator|+
name|f
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
comment|//System.out.println(translatedFile.getPath());
if|if
condition|(
name|translatedFile
operator|.
name|exists
argument_list|()
condition|)
block|{
try|try
block|{
name|Util
operator|.
name|pr
argument_list|(
literal|"file:"
operator|+
name|translatedFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|URL
name|translatedURL
init|=
operator|new
name|URL
argument_list|(
literal|"file:"
operator|+
name|translatedFile
operator|.
name|getPath
argument_list|()
argument_list|)
decl_stmt|;
name|setPageOnly
argument_list|(
name|translatedURL
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
comment|//(MalformedURLException ex) {}
block|}
else|else
block|{
name|setPageOnly
argument_list|(
name|url
argument_list|)
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Error: could not read help file: '"
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
block|}
end_class

end_unit

