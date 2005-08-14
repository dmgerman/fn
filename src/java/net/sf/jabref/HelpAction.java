begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_comment
comment|/**  * This Action keeps a reference to a URL. When activated, it shows the help  * Dialog unless it is already visible, and shows the URL in it.  */
end_comment

begin_class
DECL|class|HelpAction
specifier|public
class|class
name|HelpAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|diag
specifier|protected
name|HelpDialog
name|diag
decl_stmt|;
DECL|field|helpfile
specifier|protected
name|URL
name|helpfile
decl_stmt|;
DECL|field|helpFile
specifier|protected
name|String
name|helpFile
decl_stmt|;
DECL|method|HelpAction (HelpDialog diag, String helpFile)
specifier|public
name|HelpAction
parameter_list|(
name|HelpDialog
name|diag
parameter_list|,
name|String
name|helpFile
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|helpIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
literal|"Help"
argument_list|)
expr_stmt|;
name|this
operator|.
name|diag
operator|=
name|diag
expr_stmt|;
name|this
operator|.
name|helpFile
operator|=
name|helpFile
expr_stmt|;
block|}
DECL|method|HelpAction (HelpDialog diag, String helpFile, String tooltip)
specifier|public
name|HelpAction
parameter_list|(
name|HelpDialog
name|diag
parameter_list|,
name|String
name|helpFile
parameter_list|,
name|String
name|tooltip
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|helpIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
literal|"Help"
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
name|tooltip
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|diag
operator|=
name|diag
expr_stmt|;
name|this
operator|.
name|helpFile
operator|=
name|helpFile
expr_stmt|;
block|}
DECL|method|HelpAction (HelpDialog diag, String helpFile, String tooltip, URL iconFile)
specifier|public
name|HelpAction
parameter_list|(
name|HelpDialog
name|diag
parameter_list|,
name|String
name|helpFile
parameter_list|,
name|String
name|tooltip
parameter_list|,
name|URL
name|iconFile
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|iconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
literal|"Help"
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
name|tooltip
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|diag
operator|=
name|diag
expr_stmt|;
name|this
operator|.
name|helpFile
operator|=
name|helpFile
expr_stmt|;
block|}
DECL|method|HelpAction (String title, HelpDialog diag, String helpFile, String tooltip)
specifier|public
name|HelpAction
parameter_list|(
name|String
name|title
parameter_list|,
name|HelpDialog
name|diag
parameter_list|,
name|String
name|helpFile
parameter_list|,
name|String
name|tooltip
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|helpIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
name|tooltip
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|diag
operator|=
name|diag
expr_stmt|;
name|this
operator|.
name|helpFile
operator|=
name|helpFile
expr_stmt|;
block|}
DECL|method|HelpAction (String title, HelpDialog diag, String helpFile, String tooltip, KeyStroke key)
specifier|public
name|HelpAction
parameter_list|(
name|String
name|title
parameter_list|,
name|HelpDialog
name|diag
parameter_list|,
name|String
name|helpFile
parameter_list|,
name|String
name|tooltip
parameter_list|,
name|KeyStroke
name|key
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|helpIconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
name|tooltip
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|ACCELERATOR_KEY
argument_list|,
name|key
argument_list|)
expr_stmt|;
name|this
operator|.
name|diag
operator|=
name|diag
expr_stmt|;
name|this
operator|.
name|helpFile
operator|=
name|helpFile
expr_stmt|;
block|}
DECL|method|HelpAction (String title, HelpDialog diag, String helpFile, String tooltip, URL iconFile)
specifier|public
name|HelpAction
parameter_list|(
name|String
name|title
parameter_list|,
name|HelpDialog
name|diag
parameter_list|,
name|String
name|helpFile
parameter_list|,
name|String
name|tooltip
parameter_list|,
name|URL
name|iconFile
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|iconFile
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
name|tooltip
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|diag
operator|=
name|diag
expr_stmt|;
name|this
operator|.
name|helpFile
operator|=
name|helpFile
expr_stmt|;
block|}
comment|/*     public HelpAction(HelpDialog diag, URL helpfile, String tooltip) { 	super(Globals.lang("Help"), new ImageIcon(GUIGlobals.helpIconFile)); 	putValue(SHORT_DESCRIPTION, Globals.lang(tooltip)); 	this.diag = diag; 	this.helpfile = helpfile;               Util.pr("6. HelpAction: "+helpFile+" "+helpfile);     }      public HelpAction(HelpDialog diag, URL helpfile, String tooltip, 		      URL iconFile) { 	super(Globals.lang("Help"), new ImageIcon(iconFile)); 	putValue(SHORT_DESCRIPTION, Globals.lang(tooltip)); 	this.diag = diag; 	this.helpfile = helpfile;               Util.pr("7. HelpAction: "+helpFile+" "+helpfile);     }      public HelpAction(String title, HelpDialog diag, URL helpfile, String tooltip) { 	super(Globals.lang(title), new ImageIcon(GUIGlobals.helpIconFile)); 	putValue(SHORT_DESCRIPTION, Globals.lang(tooltip)); 	this.diag = diag; 	this.helpfile = helpfile;               Util.pr("8. HelpAction: "+helpFile+" "+helpfile);     }      public HelpAction(String title, HelpDialog diag, URL helpfile, String tooltip, 		      KeyStroke key) { 	super(Globals.lang(title), new ImageIcon(GUIGlobals.helpIconFile)); 	putValue(SHORT_DESCRIPTION, Globals.lang(tooltip)); 	putValue(ACCELERATOR_KEY, key); 	this.diag = diag; 	this.helpfile = helpfile;               Util.pr("9. HelpAction: "+helpFile+" "+helpfile);     } */
DECL|method|getIconButton ()
specifier|public
name|JButton
name|getIconButton
parameter_list|()
block|{
name|JButton
name|hlp
init|=
operator|new
name|JButton
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|hlp
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|hlp
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|24
argument_list|,
literal|24
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|hlp
return|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|diag
operator|.
name|showPage
argument_list|(
name|helpFile
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

