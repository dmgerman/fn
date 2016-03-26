begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  Filename: $RCSfile$
end_comment

begin_comment
comment|//  Purpose:  Atom representation.
end_comment

begin_comment
comment|//  Language: Java
end_comment

begin_comment
comment|//  Compiler: JDK 1.4
end_comment

begin_comment
comment|//  Authors:  Joerg K. Wegner
end_comment

begin_comment
comment|//  Version:  $Revision$
end_comment

begin_comment
comment|//            $Date$
end_comment

begin_comment
comment|//            $Author$
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  Copyright (c) Dept. Computer Architecture, University of Tuebingen, Germany
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  This program is free software; you can redistribute it and/or modify
end_comment

begin_comment
comment|//  it under the terms of the GNU General Public License as published by
end_comment

begin_comment
comment|//  the Free Software Foundation version 2 of the License.
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//  This program is distributed in the hope that it will be useful,
end_comment

begin_comment
comment|//  but WITHOUT ANY WARRANTY; without even the implied warranty of
end_comment

begin_comment
comment|//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
end_comment

begin_comment
comment|//  GNU General Public License for more details.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_package
DECL|package|net.sf.jabref.logic.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatter
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
name|Author
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
name|AuthorList
import|;
end_import

begin_comment
comment|/**  * Create DocBook authors formatter.  */
end_comment

begin_class
DECL|class|CreateDocBookAuthors
specifier|public
class|class
name|CreateDocBookAuthors
implements|implements
name|LayoutFormatter
block|{
DECL|field|XML_CHARS
specifier|private
specifier|static
specifier|final
name|XMLChars
name|XML_CHARS
init|=
operator|new
name|XMLChars
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|100
argument_list|)
decl_stmt|;
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|getAuthors
argument_list|(
name|fieldText
argument_list|)
decl_stmt|;
name|addBody
argument_list|(
name|sb
argument_list|,
name|al
argument_list|,
literal|"author"
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|addBody (StringBuilder sb, AuthorList al, String tagName)
specifier|public
name|void
name|addBody
parameter_list|(
name|StringBuilder
name|sb
parameter_list|,
name|AuthorList
name|al
parameter_list|,
name|String
name|tagName
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|al
operator|.
name|getNumberOfAuthors
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'<'
argument_list|)
operator|.
name|append
argument_list|(
name|tagName
argument_list|)
operator|.
name|append
argument_list|(
literal|'>'
argument_list|)
expr_stmt|;
name|Author
name|a
init|=
name|al
operator|.
name|getAuthor
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|a
operator|.
name|getFirst
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|a
operator|.
name|getFirst
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<firstname>"
argument_list|)
operator|.
name|append
argument_list|(
name|CreateDocBookAuthors
operator|.
name|XML_CHARS
operator|.
name|format
argument_list|(
name|a
operator|.
name|getFirst
argument_list|()
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</firstname>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|a
operator|.
name|getVon
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|a
operator|.
name|getVon
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<othername>"
argument_list|)
operator|.
name|append
argument_list|(
name|CreateDocBookAuthors
operator|.
name|XML_CHARS
operator|.
name|format
argument_list|(
name|a
operator|.
name|getVon
argument_list|()
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</othername>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|a
operator|.
name|getLast
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|a
operator|.
name|getLast
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<surname>"
argument_list|)
operator|.
name|append
argument_list|(
name|CreateDocBookAuthors
operator|.
name|XML_CHARS
operator|.
name|format
argument_list|(
name|a
operator|.
name|getLast
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|a
operator|.
name|getJr
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|a
operator|.
name|getJr
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
name|CreateDocBookAuthors
operator|.
name|XML_CHARS
operator|.
name|format
argument_list|(
name|a
operator|.
name|getJr
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</surname>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|<
operator|(
name|al
operator|.
name|getNumberOfAuthors
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"</"
argument_list|)
operator|.
name|append
argument_list|(
name|tagName
argument_list|)
operator|.
name|append
argument_list|(
literal|">\n       "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"</"
argument_list|)
operator|.
name|append
argument_list|(
name|tagName
argument_list|)
operator|.
name|append
argument_list|(
literal|'>'
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

