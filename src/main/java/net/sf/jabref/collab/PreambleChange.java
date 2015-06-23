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
name|Globals
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
name|undo
operator|.
name|UndoablePreambleChange
import|;
end_import

begin_class
DECL|class|PreambleChange
specifier|public
class|class
name|PreambleChange
extends|extends
name|Change
block|{
DECL|field|tmp
specifier|final
name|String
name|tmp
decl_stmt|;
DECL|field|mem
specifier|final
name|String
name|mem
decl_stmt|;
DECL|field|disk
specifier|final
name|String
name|disk
decl_stmt|;
DECL|field|tp
specifier|final
name|InfoPane
name|tp
init|=
operator|new
name|InfoPane
argument_list|()
decl_stmt|;
DECL|field|sp
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
DECL|method|PreambleChange (String tmp, String mem, String disk)
specifier|public
name|PreambleChange
parameter_list|(
name|String
name|tmp
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
literal|"Changed preamble"
argument_list|)
expr_stmt|;
name|this
operator|.
name|disk
operator|=
name|disk
expr_stmt|;
name|this
operator|.
name|mem
operator|=
name|mem
expr_stmt|;
name|this
operator|.
name|tmp
operator|=
name|tmp
expr_stmt|;
name|StringBuffer
name|text
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<FONT SIZE=3>"
argument_list|)
expr_stmt|;
name|text
operator|.
name|append
argument_list|(
literal|"<H2>"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Changed preamble"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</H2>"
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|disk
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|disk
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Value set externally"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
operator|+
literal|"<CODE>"
argument_list|)
operator|.
name|append
argument_list|(
name|disk
argument_list|)
operator|.
name|append
argument_list|(
literal|"</CODE>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Value cleared externally"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</H3>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|mem
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|mem
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|text
operator|.
name|append
argument_list|(
literal|"<H3>"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Current value"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|":</H3>"
operator|+
literal|"<CODE>"
argument_list|)
operator|.
name|append
argument_list|(
name|mem
argument_list|)
operator|.
name|append
argument_list|(
literal|"</CODE>"
argument_list|)
expr_stmt|;
block|}
comment|//tp.setContentType("text/html");
name|tp
operator|.
name|setText
argument_list|(
name|text
operator|.
name|toString
argument_list|()
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
name|panel
operator|.
name|database
argument_list|()
operator|.
name|setPreamble
argument_list|(
name|disk
argument_list|)
expr_stmt|;
name|undoEdit
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoablePreambleChange
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|panel
argument_list|,
name|mem
argument_list|,
name|disk
argument_list|)
argument_list|)
expr_stmt|;
name|secondary
operator|.
name|setPreamble
argument_list|(
name|disk
argument_list|)
expr_stmt|;
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

