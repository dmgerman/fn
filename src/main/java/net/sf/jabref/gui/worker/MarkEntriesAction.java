begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
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
name|BasePanel
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
name|EntryMarker
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
name|JabRefFrame
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
name|undo
operator|.
name|NamedCompound
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|awt
operator|.
name|event
operator|.
name|ActionEvent
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
name|ActionListener
import|;
end_import

begin_comment
comment|/**  *  */
end_comment

begin_class
DECL|class|MarkEntriesAction
specifier|public
class|class
name|MarkEntriesAction
extends|extends
name|AbstractWorker
implements|implements
name|ActionListener
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|level
specifier|private
specifier|final
name|int
name|level
decl_stmt|;
DECL|field|menuItem
specifier|private
specifier|final
name|JMenuItem
name|menuItem
decl_stmt|;
DECL|field|besLength
specifier|private
name|int
name|besLength
decl_stmt|;
DECL|method|MarkEntriesAction (JabRefFrame frame, int level)
specifier|public
name|MarkEntriesAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|int
name|level
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|level
operator|=
name|level
expr_stmt|;
comment|//menuItem = new JMenuItem(Globals.menuTitle("Mark entries").replaceAll("&",""));
name|menuItem
operator|=
operator|new
name|JMenuItem
argument_list|(
literal|"               "
argument_list|)
expr_stmt|;
name|menuItem
operator|.
name|setMnemonic
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|level
operator|+
literal|1
argument_list|)
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|menuItem
operator|.
name|setBackground
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"markedEntryBackground"
operator|+
name|this
operator|.
name|level
argument_list|)
argument_list|)
expr_stmt|;
name|menuItem
operator|.
name|setOpaque
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|menuItem
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|getMenuItem ()
specifier|public
name|JMenuItem
name|getMenuItem
parameter_list|()
block|{
return|return
name|menuItem
return|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent actionEvent)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
try|try
block|{
name|this
operator|.
name|init
argument_list|()
expr_stmt|;
name|getWorker
argument_list|()
operator|.
name|run
argument_list|()
expr_stmt|;
name|getCallBack
argument_list|()
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|t
parameter_list|)
block|{
name|t
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|BasePanel
name|panel
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
name|BibEntry
index|[]
name|bes
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
comment|// used at update() to determine output string
name|besLength
operator|=
name|bes
operator|.
name|length
expr_stmt|;
if|if
condition|(
name|bes
operator|.
name|length
operator|!=
literal|0
condition|)
block|{
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Mark entries"
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|be
range|:
name|bes
control|)
block|{
name|EntryMarker
operator|.
name|markEntry
argument_list|(
name|be
argument_list|,
name|level
operator|+
literal|1
argument_list|,
literal|false
argument_list|,
name|ce
argument_list|)
expr_stmt|;
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|String
name|outputStr
decl_stmt|;
switch|switch
condition|(
name|besLength
condition|)
block|{
case|case
literal|0
case|:
name|outputStr
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"No entries selected."
argument_list|)
expr_stmt|;
break|break;
case|case
literal|1
case|:
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|outputStr
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Marked selected entry"
argument_list|)
expr_stmt|;
break|break;
default|default:
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|outputStr
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Marked all %0 selected entries"
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|besLength
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
name|frame
operator|.
name|output
argument_list|(
name|outputStr
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

