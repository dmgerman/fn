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
comment|//  Version:  $Revision: 2268 $
end_comment

begin_comment
comment|//            $Date: 2007-08-20 01:37:05 +0200 (Mon, 20 Aug 2007) $
end_comment

begin_comment
comment|//            $Author: coezbek $
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
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * @author $author$  * @version $Revision: 2268 $  */
end_comment

begin_class
DECL|class|CreateBibORDFAuthors
specifier|public
class|class
name|CreateBibORDFAuthors
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
comment|// Yeah, the format is quite verbose... sorry about that :)
comment|//<bibo:contribution>
comment|//<bibo:Contribution>
comment|//<bibo:role rdf:resource="http://purl.org/ontology/bibo/roles/author" />
comment|//<bibo:contributor><foaf:Person foaf:name="Ola Spjuth"/></bibo:contributor>
comment|//<bibo:position>1</bibo:position>
comment|//</bibo:Contribution>
comment|//</bibo:contribution>
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|(
literal|100
argument_list|)
decl_stmt|;
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
name|singleAuthor
argument_list|(
name|sb
argument_list|,
name|fieldText
argument_list|,
literal|1
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
name|singleAuthor
argument_list|(
name|sb
argument_list|,
name|names
index|[
name|i
index|]
argument_list|,
operator|(
name|i
operator|+
literal|1
operator|)
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
literal|"\n"
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
DECL|method|singleAuthor (StringBuffer sb, String author, int position)
specifier|protected
name|void
name|singleAuthor
parameter_list|(
name|StringBuffer
name|sb
parameter_list|,
name|String
name|author
parameter_list|,
name|int
name|position
parameter_list|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<bibo:contribution>\n"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<bibo:Contribution>\n"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<bibo:role rdf:resource=\"http://purl.org/ontology/bibo/roles/author\" />\n"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<bibo:contributor><foaf:Person foaf:name=\""
operator|+
name|author
operator|+
literal|"\"/></bibo:contributor>\n"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<bibo:position>"
operator|+
name|position
operator|+
literal|"</bibo:position>\n"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</bibo:Contribution>\n"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</bibo:contribution>\n"
argument_list|)
expr_stmt|;
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

