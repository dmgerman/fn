begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2004 R. Nagel  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_comment
comment|// created by : r.nagel 13.10.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// function : handles the subdatabase from aux command line option
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified :
end_comment

begin_package
DECL|package|net.sf.jabref.wizard.auximport
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|wizard
operator|.
name|auximport
package|;
end_package

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
name|logic
operator|.
name|util
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|AuxCommandLine
specifier|public
class|class
name|AuxCommandLine
block|{
DECL|field|auxName
specifier|private
specifier|final
name|String
name|auxName
decl_stmt|;
DECL|field|bib
specifier|private
specifier|final
name|BibtexDatabase
name|bib
decl_stmt|;
DECL|method|AuxCommandLine (String auxFileName, BibtexDatabase refDBase)
specifier|public
name|AuxCommandLine
parameter_list|(
name|String
name|auxFileName
parameter_list|,
name|BibtexDatabase
name|refDBase
parameter_list|)
block|{
name|auxName
operator|=
name|StringUtil
operator|.
name|getCorrectFileName
argument_list|(
name|auxFileName
argument_list|,
literal|"aux"
argument_list|)
expr_stmt|;
name|bib
operator|=
name|refDBase
expr_stmt|;
block|}
DECL|method|perform ()
specifier|public
name|BibtexDatabase
name|perform
parameter_list|()
block|{
name|BibtexDatabase
name|back
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|!
name|auxName
operator|.
name|isEmpty
argument_list|()
operator|&&
name|bib
operator|!=
literal|null
condition|)
block|{
name|AuxSubGenerator
name|auxParser
init|=
operator|new
name|AuxSubGenerator
argument_list|(
name|bib
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|returnValue
init|=
name|auxParser
operator|.
name|generate
argument_list|(
name|auxName
argument_list|,
name|bib
argument_list|)
decl_stmt|;
name|back
operator|=
name|auxParser
operator|.
name|getGeneratedDatabase
argument_list|()
expr_stmt|;
comment|// print statistics
comment|//      System.out.println(Globals.lang( "Results" ));
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"keys_in_database"
argument_list|)
operator|+
literal|" "
operator|+
name|bib
operator|.
name|getEntryCount
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"found_in_aux_file"
argument_list|)
operator|+
literal|" "
operator|+
name|auxParser
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"resolved"
argument_list|)
operator|+
literal|" "
operator|+
name|auxParser
operator|.
name|getResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|auxParser
operator|.
name|getNotResolvedKeysCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"not_found"
argument_list|)
operator|+
literal|" "
operator|+
name|auxParser
operator|.
name|getNotResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|returnValue
argument_list|)
expr_stmt|;
block|}
name|int
name|nested
init|=
name|auxParser
operator|.
name|getNestedAuxCounter
argument_list|()
decl_stmt|;
if|if
condition|(
name|nested
operator|>
literal|0
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"nested_aux_files"
argument_list|)
operator|+
literal|" "
operator|+
name|nested
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|back
return|;
block|}
block|}
end_class

end_unit

