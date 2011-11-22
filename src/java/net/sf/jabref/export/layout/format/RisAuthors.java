begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
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
name|export
operator|.
name|layout
operator|.
name|*
import|;
end_import

begin_class
DECL|class|RisAuthors
specifier|public
class|class
name|RisAuthors
implements|implements
name|ParamLayoutFormatter
block|{
DECL|field|arg
specifier|private
name|String
name|arg
init|=
literal|""
decl_stmt|;
DECL|method|format (String s)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
name|s
operator|==
literal|null
condition|)
return|return
literal|""
return|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|String
index|[]
name|authors
init|=
name|AuthorList
operator|.
name|fixAuthor_lastNameFirst
argument_list|(
name|s
argument_list|)
operator|.
name|split
argument_list|(
literal|" and "
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|authors
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|arg
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"  - "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|authors
index|[
name|i
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|authors
operator|.
name|length
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|setArgument (String arg)
specifier|public
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
block|{
name|this
operator|.
name|arg
operator|=
name|arg
expr_stmt|;
block|}
block|}
end_class

end_unit

