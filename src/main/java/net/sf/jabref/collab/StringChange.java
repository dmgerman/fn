begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
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
name|model
operator|.
name|database
operator|.
name|KeyCollisionException
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
name|IdGenerator
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
name|database
operator|.
name|BibDatabase
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
name|BibtexString
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
name|gui
operator|.
name|undo
operator|.
name|UndoableInsertString
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
name|UndoableStringChange
import|;
end_import

begin_class
DECL|class|StringChange
class|class
name|StringChange
extends|extends
name|Change
block|{
DECL|field|string
specifier|private
specifier|final
name|BibtexString
name|string
decl_stmt|;
DECL|field|mem
specifier|private
specifier|final
name|String
name|mem
decl_stmt|;
DECL|field|disk
specifier|private
specifier|final
name|String
name|disk
decl_stmt|;
DECL|field|label
specifier|private
specifier|final
name|String
name|label
decl_stmt|;
DECL|field|tp
specifier|private
specifier|final
name|InfoPane
name|tp
init|=
operator|new
name|InfoPane
argument_list|()
decl_stmt|;
DECL|field|sp
specifier|private
specifier|final
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|tp
argument_list|)
decl_stmt|;
DECL|field|tmpString
specifier|private
specifier|final
name|BibtexString
name|tmpString
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
name|StringChange
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|StringChange (BibtexString string, BibtexString tmpString, String label, String mem, String disk)
specifier|public
name|StringChange
parameter_list|(
name|BibtexString
name|string
parameter_list|,
name|BibtexString
name|tmpString
parameter_list|,
name|String
name|label
parameter_list|,
name|String
name|mem
parameter_list|,
name|String
name|disk
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modified string"
argument_list|)
operator|+
literal|": '"
operator|+
name|label
operator|+
literal|'\''
argument_list|)
expr_stmt|;
name|this
operator|.
name|tmpString
operator|=
name|tmpString
expr_stmt|;
name|this
operator|.
name|string
operator|=
name|string
expr_stmt|;
name|this
operator|.
name|label
operator|=
name|label
expr_stmt|;
name|this
operator|.
name|mem
operator|=
name|mem
expr_stmt|;
name|this
operator|.
name|disk
operator|=
name|disk
expr_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<HTML><H2>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modified string"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</H2><H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Label"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"New content"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|disk
argument_list|)
expr_stmt|;
if|if
condition|(
name|string
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Current content"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|string
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<P><I>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot merge this change"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|": "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"The string has been removed locally"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</I>"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</HTML>"
argument_list|)
expr_stmt|;
name|tp
operator|.
name|setText
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BasePanel panel, BibDatabase secondary, NamedCompound undoEdit)
specifier|public
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
if|if
condition|(
name|string
operator|!=
literal|null
condition|)
block|{
name|string
operator|.
name|setContent
argument_list|(
name|disk
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableStringChange
argument_list|(
name|panel
argument_list|,
name|string
argument_list|,
literal|false
argument_list|,
name|mem
argument_list|,
name|disk
argument_list|)
argument_list|)
expr_stmt|;
comment|// Update tmp databse:
block|}
else|else
block|{
comment|// The string was removed or renamed locally. We guess that it was removed.
name|String
name|newId
init|=
name|IdGenerator
operator|.
name|next
argument_list|()
decl_stmt|;
name|BibtexString
name|bs
init|=
operator|new
name|BibtexString
argument_list|(
name|newId
argument_list|,
name|label
argument_list|,
name|disk
argument_list|)
decl_stmt|;
try|try
block|{
name|panel
operator|.
name|database
argument_list|()
operator|.
name|addString
argument_list|(
name|bs
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertString
argument_list|(
name|panel
argument_list|,
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|bs
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Error: could not add string '"
operator|+
name|bs
operator|.
name|getName
argument_list|()
operator|+
literal|"': "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Update tmp database:
if|if
condition|(
name|tmpString
operator|!=
literal|null
condition|)
block|{
name|tmpString
operator|.
name|setContent
argument_list|(
name|disk
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|BibtexString
name|bs
init|=
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|label
argument_list|,
name|disk
argument_list|)
decl_stmt|;
name|secondary
operator|.
name|addString
argument_list|(
name|bs
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|description ()
name|JComponent
name|description
parameter_list|()
block|{
return|return
name|sp
return|;
block|}
block|}
end_class

end_unit

