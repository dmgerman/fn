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
DECL|package|net.sf.jabref.export.layout
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
package|;
end_package

begin_comment
comment|/*==========================================================================*  * IMPORTS  *==========================================================================*/
end_comment

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
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
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
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintStream
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
name|imports
operator|.
name|BibtexParser
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
name|ParserResult
import|;
end_import

begin_comment
comment|/*==========================================================================*  * CLASS DECLARATION  *==========================================================================     */
end_comment

begin_comment
comment|/**  * Example for converting molecules.  *  * @author     wegnerj  */
end_comment

begin_class
DECL|class|LayoutTest
specifier|public
class|class
name|LayoutTest
block|{
comment|//~ Constructors ///////////////////////////////////////////////////////////
DECL|method|LayoutTest ()
specifier|public
name|LayoutTest
parameter_list|()
block|{     }
comment|//~ Methods ////////////////////////////////////////////////////////////////
comment|/**      *  The main program for the TestSmarts class      *      * @param  args  The command line arguments      */
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|LayoutTest
name|test
init|=
literal|null
decl_stmt|;
name|test
operator|=
operator|new
name|LayoutTest
argument_list|()
expr_stmt|;
if|if
condition|(
name|args
operator|.
name|length
operator|!=
literal|3
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Usage: LayoutTest<BibTeX-File><Layout-File><Output-File>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|test
operator|.
name|test
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|,
name|args
index|[
literal|1
index|]
argument_list|,
name|args
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      *      */
DECL|method|test (String bibtexFile, String layoutFile, String outputFile)
specifier|private
name|void
name|test
parameter_list|(
name|String
name|bibtexFile
parameter_list|,
name|String
name|layoutFile
parameter_list|,
name|String
name|outputFile
parameter_list|)
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|bibtexFile
argument_list|)
decl_stmt|;
name|File
name|outFile
init|=
operator|new
name|File
argument_list|(
name|outputFile
argument_list|)
decl_stmt|;
name|BibtexParser
name|bparser
init|=
literal|null
decl_stmt|;
name|BibtexDatabase
name|bibtex
init|=
literal|null
decl_stmt|;
name|PrintStream
name|ps
init|=
literal|null
decl_stmt|;
try|try
block|{
name|FileInputStream
name|fis
init|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|InputStreamReader
name|reader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|fis
argument_list|)
decl_stmt|;
name|ps
operator|=
operator|new
name|PrintStream
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|outFile
argument_list|)
argument_list|)
expr_stmt|;
name|bparser
operator|=
operator|new
name|BibtexParser
argument_list|(
name|reader
argument_list|)
expr_stmt|;
name|ParserResult
name|pr
init|=
name|bparser
operator|.
name|parse
argument_list|()
decl_stmt|;
name|bibtex
operator|=
name|pr
operator|.
name|getDatabase
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|file
operator|=
operator|new
name|File
argument_list|(
name|layoutFile
argument_list|)
expr_stmt|;
try|try
block|{
name|FileInputStream
name|fis
init|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|InputStreamReader
name|reader
init|=
operator|new
name|InputStreamReader
argument_list|(
name|fis
argument_list|)
decl_stmt|;
name|LayoutHelper
name|layoutHelper
init|=
operator|new
name|LayoutHelper
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|Layout
name|layout
init|=
name|layoutHelper
operator|.
name|getLayoutFromText
argument_list|(
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
operator|.
name|FORMATTER_PACKAGE
argument_list|)
decl_stmt|;
name|Object
index|[]
name|keys
init|=
name|bibtex
operator|.
name|getKeySet
argument_list|()
operator|.
name|toArray
argument_list|()
decl_stmt|;
name|String
name|key
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
name|keys
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|key
operator|=
operator|(
name|String
operator|)
name|keys
index|[
name|i
index|]
expr_stmt|;
comment|//System.out.println(key);
name|BibtexEntry
name|entry
init|=
name|bibtex
operator|.
name|getEntryById
argument_list|(
name|key
argument_list|)
decl_stmt|;
comment|//System.out.println(layout.doLayout(entry));
name|ps
operator|.
name|println
argument_list|(
name|layout
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
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

