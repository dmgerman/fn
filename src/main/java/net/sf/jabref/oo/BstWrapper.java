begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
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
name|BibtexEntry
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
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|FormatChars
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
name|bst
operator|.
name|VM
import|;
end_import

begin_import
import|import
name|org
operator|.
name|antlr
operator|.
name|runtime
operator|.
name|RecognitionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_comment
comment|/**  *  * Wrapper for using JabRef's bst engine for formatting OO bibliography.  */
end_comment

begin_class
DECL|class|BstWrapper
class|class
name|BstWrapper
block|{
DECL|field|formatter
specifier|private
specifier|final
name|LayoutFormatter
name|formatter
init|=
operator|new
name|FormatChars
argument_list|()
decl_stmt|;
DECL|field|vm
specifier|private
name|VM
name|vm
decl_stmt|;
DECL|method|BstWrapper ()
specifier|public
name|BstWrapper
parameter_list|()
block|{      }
comment|/**      * Set the bst file to be used for processing. This method will initiate parsing      * of the bst file.      * @param f The bst file to load.      * @throws IOException On IO errors.      * @throws RecognitionException On parsing errors.      */
DECL|method|loadBstFile (File f)
specifier|public
name|void
name|loadBstFile
parameter_list|(
name|File
name|f
parameter_list|)
throws|throws
name|IOException
throws|,
name|RecognitionException
block|{
name|vm
operator|=
operator|new
name|VM
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
comment|/**      * Use the instructions of the loaded bst file for processing a collection of entries.      * @param entries The entries to process.      * @param database The database the entries belong to.      * @return A Map of the entries' bibtex keys linking to their processed strings.      */
DECL|method|processEntries (Collection<BibtexEntry> entries, BibtexDatabase database)
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|processEntries
parameter_list|(
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
comment|// TODO: how to handle uniquefiers?
comment|// TODO: need handling of crossrefs?
name|String
name|result
init|=
name|vm
operator|.
name|run
argument_list|(
name|entries
argument_list|)
decl_stmt|;
return|return
name|parseResult
argument_list|(
name|result
argument_list|)
return|;
block|}
DECL|field|bibitemTag
specifier|private
specifier|static
specifier|final
name|Pattern
name|bibitemTag
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\\\[a-zA-Z]*item\\{.*\\}"
argument_list|)
decl_stmt|;
DECL|method|parseResult (String result)
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|parseResult
parameter_list|(
name|String
name|result
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
comment|// Look through for instances of \bibitem :
name|Matcher
name|m
init|=
name|BstWrapper
operator|.
name|bibitemTag
operator|.
name|matcher
argument_list|(
name|result
argument_list|)
decl_stmt|;
name|ArrayList
argument_list|<
name|Integer
argument_list|>
name|indices
init|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|Integer
argument_list|>
name|endIndices
init|=
operator|new
name|ArrayList
argument_list|<
name|Integer
argument_list|>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|keys
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|indices
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|endIndices
operator|.
name|add
argument_list|(
name|m
operator|.
name|start
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|m
operator|.
name|start
argument_list|()
operator|+
literal|"  "
operator|+
name|m
operator|.
name|end
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|tag
init|=
name|m
operator|.
name|group
argument_list|()
decl_stmt|;
name|String
name|key
init|=
name|tag
operator|.
name|substring
argument_list|(
literal|9
argument_list|,
name|tag
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
name|indices
operator|.
name|add
argument_list|(
name|m
operator|.
name|end
argument_list|()
argument_list|)
expr_stmt|;
name|keys
operator|.
name|add
argument_list|(
name|key
argument_list|)
expr_stmt|;
block|}
name|int
name|lastI
init|=
name|result
operator|.
name|lastIndexOf
argument_list|(
literal|"\\end{thebibliography}"
argument_list|)
decl_stmt|;
if|if
condition|(
name|lastI
operator|>
literal|0
operator|&&
name|lastI
operator|>
name|indices
operator|.
name|get
argument_list|(
name|indices
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
condition|)
block|{
name|endIndices
operator|.
name|add
argument_list|(
name|lastI
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|keys
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|key
init|=
name|keys
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|int
name|index
init|=
name|indices
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|int
name|endIndex
init|=
name|endIndices
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|part
init|=
name|result
operator|.
name|substring
argument_list|(
name|index
argument_list|,
name|endIndex
argument_list|)
decl_stmt|;
name|map
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|part
operator|.
name|trim
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\\\newblock "
argument_list|,
literal|" "
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|map
return|;
block|}
block|}
end_class

end_unit

