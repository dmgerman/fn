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
DECL|package|net.sf.jabref.exporter.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
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
name|exporter
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
operator|!
name|fieldText
operator|.
name|contains
argument_list|(
literal|" and "
argument_list|)
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
name|i
operator|+
literal|1
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
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
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
comment|/**      * @param sb      * @param author      * @param position      */
DECL|method|singleAuthor (StringBuffer sb, String author, int position)
specifier|private
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
argument_list|)
operator|.
name|append
argument_list|(
name|author
argument_list|)
operator|.
name|append
argument_list|(
literal|"\"/></bibo:contributor>\n"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<bibo:position>"
argument_list|)
operator|.
name|append
argument_list|(
name|position
argument_list|)
operator|.
name|append
argument_list|(
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

end_unit

