begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|export
operator|.
name|layout
operator|.
name|WSITools
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|AuthorList
import|;
end_import

begin_comment
comment|/**  * Create DocBook authors formatter.  *  * @author $author$  * @version $Revision$  */
end_comment

begin_class
DECL|class|CreateDocBookAuthors
specifier|public
class|class
name|CreateDocBookAuthors
implements|implements
name|LayoutFormatter
block|{
comment|//~ Methods ////////////////////////////////////////////////////////////////
DECL|field|xc
specifier|static
specifier|final
name|XMLChars
name|xc
init|=
operator|new
name|XMLChars
argument_list|()
decl_stmt|;
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
name|getAuthorList
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
comment|//<author><firstname>L.</firstname><surname>Xue</surname></author>
comment|//<author><firstname>F.</firstname><othername role="mi">L.</othername><surname>Stahura</surname></author>
comment|//<author><firstname>J.</firstname><othername role="mi">W.</othername><surname>Godden</surname></author>
comment|//<author><firstname>J.</firstname><surname>Bajorath</surname></author>
comment|/*         if (fieldText.indexOf(" and ") == -1)         {           sb.append("<author>");           singleAuthor(sb, fieldText);           sb.append("</author>");         }         else         {             String[] names = fieldText.split(" and ");             for (int i=0; i<names.length; i++)             {               sb.append("<author>");               singleAuthor(sb, names[i]);               sb.append("</author>");               if (i< names.length -1)                 sb.append("\n       ");             }         }            fieldText = sb.toString();          return fieldText;*/
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
name|size
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
literal|"<"
argument_list|)
operator|.
name|append
argument_list|(
name|tagName
argument_list|)
operator|.
name|append
argument_list|(
literal|">"
argument_list|)
expr_stmt|;
name|AuthorList
operator|.
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
operator|(
name|a
operator|.
name|getFirst
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<firstname>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|xc
operator|.
name|format
argument_list|(
name|a
operator|.
name|getFirst
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
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
operator|(
name|a
operator|.
name|getVon
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<othername>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|xc
operator|.
name|format
argument_list|(
name|a
operator|.
name|getVon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
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
operator|(
name|a
operator|.
name|getLast
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<surname>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|xc
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
operator|(
name|a
operator|.
name|getJr
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|" "
argument_list|)
operator|.
name|append
argument_list|(
name|xc
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
name|al
operator|.
name|size
argument_list|()
operator|-
literal|1
condition|)
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
else|else
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
literal|">"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param sb      * @param author      */
DECL|method|singleAuthor (StringBuffer sb, String author)
specifier|protected
name|void
name|singleAuthor
parameter_list|(
name|StringBuffer
name|sb
parameter_list|,
name|String
name|author
parameter_list|)
block|{
comment|// TODO: replace special characters
name|Vector
argument_list|<
name|String
argument_list|>
name|v
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|String
name|authorMod
init|=
name|AuthorList
operator|.
name|fixAuthor_firstNameFirst
argument_list|(
name|author
argument_list|)
decl_stmt|;
name|WSITools
operator|.
name|tokenize
argument_list|(
name|v
argument_list|,
name|authorMod
argument_list|,
literal|" \n\r"
argument_list|)
expr_stmt|;
if|if
condition|(
name|v
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<surname>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|v
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</surname>"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|v
operator|.
name|size
argument_list|()
operator|==
literal|2
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<firstname>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|v
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</firstname>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<surname>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|v
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</surname>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<firstname>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|v
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</firstname>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<othername role=\"mi\">"
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
operator|(
name|v
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|v
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
operator|(
name|v
operator|.
name|size
argument_list|()
operator|-
literal|2
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
block|}
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</othername>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<surname>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|v
operator|.
name|get
argument_list|(
name|v
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</surname>"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

begin_comment
comment|//  END OF FILE.
end_comment

begin_comment
comment|///////////////////////////////////////////////////////////////////////////////
end_comment

end_unit

