begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.external.push
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
operator|.
name|push
package|;
end_package

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
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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

begin_comment
comment|/**  * An Action class representing the process of invoking a PushToApplication operation.  */
end_comment

begin_class
DECL|class|PushToApplicationAction
class|class
name|PushToApplicationAction
extends|extends
name|AbstractAction
implements|implements
name|Runnable
block|{
DECL|field|operation
specifier|private
specifier|final
name|PushToApplication
name|operation
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|entries
specifier|private
name|BibEntry
index|[]
name|entries
decl_stmt|;
DECL|method|PushToApplicationAction (JabRefFrame frame, PushToApplication operation)
specifier|public
name|PushToApplicationAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|PushToApplication
name|operation
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SMALL_ICON
argument_list|,
name|operation
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|operation
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|operation
operator|.
name|getTooltip
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|operation
operator|=
name|operation
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
expr_stmt|;
comment|// Check if a BasePanel exists:
if|if
condition|(
name|panel
operator|==
literal|null
condition|)
block|{
return|return;
block|}
comment|// Check if any entries are selected:
name|entries
operator|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"This operation requires one or more entries to be selected."
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|getValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// If required, check that all entries have BibTeX keys defined:
if|if
condition|(
name|operation
operator|.
name|requiresBibtexKeys
argument_list|()
condition|)
block|{
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
operator|(
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|==
literal|null
operator|)
operator|||
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"This operation requires all selected entries to have BibTex keys defined."
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|getValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
block|}
comment|// All set, call the operation in a new thread:
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|// Do the operation:
name|operation
operator|.
name|pushEntries
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|entries
argument_list|,
name|getKeyString
argument_list|(
name|entries
argument_list|)
argument_list|,
name|panel
operator|.
name|metaData
argument_list|()
argument_list|)
expr_stmt|;
comment|// Call the operationCompleted() method on the event dispatch thread:
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|operation
operator|.
name|operationCompleted
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|getKeyString (BibEntry[] bibentries)
specifier|private
specifier|static
name|String
name|getKeyString
parameter_list|(
name|BibEntry
index|[]
name|bibentries
parameter_list|)
block|{
name|StringBuilder
name|result
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|String
name|citeKey
decl_stmt|;
name|boolean
name|first
init|=
literal|true
decl_stmt|;
for|for
control|(
name|BibEntry
name|bes
range|:
name|bibentries
control|)
block|{
name|citeKey
operator|=
name|bes
operator|.
name|getCiteKey
argument_list|()
expr_stmt|;
comment|// if the key is empty we give a warning and ignore this entry
if|if
condition|(
operator|(
name|citeKey
operator|==
literal|null
operator|)
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|citeKey
argument_list|)
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|first
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|citeKey
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|append
argument_list|(
literal|","
argument_list|)
operator|.
name|append
argument_list|(
name|citeKey
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

