begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|wsi
operator|.
name|ra
operator|.
name|tool
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
name|imports
operator|.
name|*
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
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
comment|//<author><firstname>L.</firstname><surname>Xue</surname></author>
comment|//<author><firstname>F.</firstname><othername role="mi">L.</othername><surname>Stahura</surname></author>
comment|//<author><firstname>J.</firstname><othername role="mi">W.</othername><surname>Godden</surname></author>
comment|//<author><firstname>J.</firstname><surname>Bajorath</surname></author>
name|int
name|index
init|=
literal|0
decl_stmt|;
name|int
name|oldPos
init|=
literal|0
decl_stmt|;
name|String
name|author
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|(
literal|100
argument_list|)
decl_stmt|;
comment|//fieldText = (new ConvertSpecialCharactersForXML()).format(fieldText);
if|if
condition|(
name|fieldText
operator|.
name|indexOf
argument_list|(
literal|" and "
argument_list|)
operator|==
operator|-
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<author>"
argument_list|)
expr_stmt|;
name|singleAuthor
argument_list|(
name|sb
argument_list|,
name|fieldText
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</author>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
index|[]
name|names
init|=
name|fieldText
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
name|names
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
literal|"<author>"
argument_list|)
expr_stmt|;
name|singleAuthor
argument_list|(
name|sb
argument_list|,
name|names
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</author>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|<
name|names
operator|.
name|length
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"\n       "
argument_list|)
expr_stmt|;
block|}
block|}
name|fieldText
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
return|return
name|fieldText
return|;
block|}
comment|/**      * @param sb      * @param fieldText      */
DECL|method|singleAuthor (StringBuffer sb, String author)
specifier|private
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
name|v
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|String
name|authorMod
init|=
name|ImportFormatReader
operator|.
name|fixAuthor
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

