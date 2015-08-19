begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|gui
operator|.
name|keyboard
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
name|logic
operator|.
name|id
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
name|BibtexDatabase
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

begin_class
DECL|class|StringAddChange
class|class
name|StringAddChange
extends|extends
name|Change
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
DECL|field|string
specifier|private
specifier|final
name|BibtexString
name|string
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
name|StringAddChange
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|StringAddChange (BibtexString string)
specifier|public
name|StringAddChange
parameter_list|(
name|BibtexString
name|string
parameter_list|)
block|{
name|name
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Added string"
argument_list|)
operator|+
literal|": '"
operator|+
name|string
operator|.
name|getName
argument_list|()
operator|+
literal|'\''
expr_stmt|;
name|this
operator|.
name|string
operator|=
name|string
expr_stmt|;
name|tp
operator|.
name|setText
argument_list|(
literal|"<HTML><H2>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Added string"
argument_list|)
operator|+
literal|"</H2><H3>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Label"
argument_list|)
operator|+
literal|":</H3>"
operator|+
name|string
operator|.
name|getName
argument_list|()
operator|+
literal|"<H3>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Content"
argument_list|)
operator|+
literal|":</H3>"
operator|+
name|string
operator|.
name|getContent
argument_list|()
operator|+
literal|"</HTML>"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BasePanel panel, BibtexDatabase secondary, NamedCompound undoEdit)
specifier|public
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibtexDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
if|if
condition|(
name|panel
operator|.
name|database
argument_list|()
operator|.
name|hasStringLabel
argument_list|(
name|string
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
comment|// The name to change to is already in the database, so we can't comply.
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot add string '"
operator|+
name|string
operator|.
name|getName
argument_list|()
operator|+
literal|"' because the name "
operator|+
literal|"is already in use."
argument_list|)
expr_stmt|;
block|}
try|try
block|{
name|panel
operator|.
name|database
argument_list|()
operator|.
name|addString
argument_list|(
name|string
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
name|string
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
name|string
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
try|try
block|{
name|secondary
operator|.
name|addString
argument_list|(
operator|new
name|BibtexString
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|string
operator|.
name|getName
argument_list|()
argument_list|,
name|string
operator|.
name|getContent
argument_list|()
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
name|string
operator|.
name|getName
argument_list|()
operator|+
literal|"' to tmp database: "
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

