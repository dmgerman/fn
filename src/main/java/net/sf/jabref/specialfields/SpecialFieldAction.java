begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|util
operator|.
name|List
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
name|actions
operator|.
name|BaseAction
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
DECL|class|SpecialFieldAction
specifier|public
class|class
name|SpecialFieldAction
implements|implements
name|BaseAction
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|specialField
specifier|private
specifier|final
name|SpecialField
name|specialField
decl_stmt|;
DECL|field|value
specifier|private
specifier|final
name|String
name|value
decl_stmt|;
DECL|field|nullFieldIfValueIsTheSame
specifier|private
specifier|final
name|boolean
name|nullFieldIfValueIsTheSame
decl_stmt|;
DECL|field|undoText
specifier|private
specifier|final
name|String
name|undoText
decl_stmt|;
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SpecialFieldAction
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      *      * @param nullFieldIfValueIsTheSame - false also causes that doneTextPattern has two place holders %0 for the value and %1 for the sum of entries      */
DECL|method|SpecialFieldAction ( JabRefFrame frame, SpecialField specialField, String value, boolean nullFieldIfValueIsTheSame, String undoText)
specifier|public
name|SpecialFieldAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|SpecialField
name|specialField
parameter_list|,
name|String
name|value
parameter_list|,
name|boolean
name|nullFieldIfValueIsTheSame
parameter_list|,
name|String
name|undoText
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
name|specialField
operator|=
name|specialField
expr_stmt|;
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
name|this
operator|.
name|nullFieldIfValueIsTheSame
operator|=
name|nullFieldIfValueIsTheSame
expr_stmt|;
name|this
operator|.
name|undoText
operator|=
name|undoText
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
block|{
try|try
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bes
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|bes
operator|==
literal|null
operator|)
operator|||
name|bes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|undoText
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
comment|// if (value==null) and then call nullField has been omitted as updatefield also handles value==null
name|SpecialFieldsUtils
operator|.
name|updateField
argument_list|(
name|specialField
argument_list|,
name|value
argument_list|,
name|be
argument_list|,
name|ce
argument_list|,
name|nullFieldIfValueIsTheSame
argument_list|)
expr_stmt|;
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
if|if
condition|(
name|ce
operator|.
name|hasEdits
argument_list|()
condition|)
block|{
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|updateEntryEditorIfShowing
argument_list|()
expr_stmt|;
name|String
name|outText
decl_stmt|;
if|if
condition|(
name|nullFieldIfValueIsTheSame
operator|||
name|value
operator|==
literal|null
condition|)
block|{
name|outText
operator|=
name|specialField
operator|.
name|getTextDone
argument_list|(
name|Integer
operator|.
name|toString
argument_list|(
name|bes
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|outText
operator|=
name|specialField
operator|.
name|getTextDone
argument_list|(
name|value
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|bes
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|frame
operator|.
name|output
argument_list|(
name|outText
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// if user does not change anything with his action, we do not do anything either
comment|// even no output message
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem setting special fields"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

