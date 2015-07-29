begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.specialfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|specialfields
package|;
end_package

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|PropertyChangeEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|PropertyVetoException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|VetoableChangeListener
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
name|BibtexEntry
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

begin_comment
comment|/**  * Listener triggering   *  * an update of keywords if special field has been updated  *  * an update of special fields if keywords have been updated   */
end_comment

begin_class
DECL|class|SpecialFieldUpdateListener
specifier|public
class|class
name|SpecialFieldUpdateListener
implements|implements
name|VetoableChangeListener
block|{
DECL|field|INSTANCE
specifier|private
specifier|static
name|SpecialFieldUpdateListener
name|INSTANCE
init|=
literal|null
decl_stmt|;
annotation|@
name|Override
DECL|method|vetoableChange (PropertyChangeEvent e)
specifier|public
name|void
name|vetoableChange
parameter_list|(
name|PropertyChangeEvent
name|e
parameter_list|)
throws|throws
name|PropertyVetoException
block|{
specifier|final
name|BibtexEntry
name|entry
init|=
operator|(
name|BibtexEntry
operator|)
name|e
operator|.
name|getSource
argument_list|()
decl_stmt|;
specifier|final
name|String
name|fieldName
init|=
name|e
operator|.
name|getPropertyName
argument_list|()
decl_stmt|;
comment|// Source editor cycles through all entries
comment|// if we immediately updated the fields, the entry editor would detect a subsequent change as a user change
comment|// and re-fire this event
comment|// e.g., "keyword = {prio1}, priority = {prio2}" and a change at keyword to prio3 would not succeed.
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
block|{
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
literal|"keywords"
argument_list|)
condition|)
block|{
comment|// we do NOT pass a named component indicating that we do not want to have undo capabilities
comment|// if the user undoes the change in the keyword field, this method is also called and
comment|// the special fields are updated accordingly
name|SpecialFieldsUtils
operator|.
name|syncSpecialFieldsFromKeywords
argument_list|(
name|entry
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|JabRef
operator|.
name|jrf
operator|.
name|basePanel
argument_list|()
operator|.
name|updateEntryEditorIfShowing
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|SpecialField
name|field
init|=
name|SpecialFieldsUtils
operator|.
name|getSpecialFieldInstanceFromFieldName
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
name|field
operator|!=
literal|null
condition|)
block|{
comment|// we do NOT pass a named component indicating that we do not want to have undo capabilities
comment|// if the user undoes the change in the sepcial field, this method is also called and
comment|// the keyword field is updated accordingly
name|SpecialFieldsUtils
operator|.
name|syncKeywordsFromSpecialFields
argument_list|(
name|entry
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|JabRef
operator|.
name|jrf
operator|.
name|basePanel
argument_list|()
operator|.
name|updateEntryEditorIfShowing
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|getInstance ()
specifier|public
specifier|static
name|SpecialFieldUpdateListener
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|SpecialFieldUpdateListener
operator|.
name|INSTANCE
operator|==
literal|null
condition|)
block|{
name|SpecialFieldUpdateListener
operator|.
name|INSTANCE
operator|=
operator|new
name|SpecialFieldUpdateListener
argument_list|()
expr_stmt|;
block|}
return|return
name|SpecialFieldUpdateListener
operator|.
name|INSTANCE
return|;
block|}
block|}
end_class

end_unit

