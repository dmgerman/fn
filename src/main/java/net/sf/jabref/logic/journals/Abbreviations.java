begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
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
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|FileNotFoundException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_class
DECL|class|Abbreviations
specifier|public
class|class
name|Abbreviations
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|Abbreviations
operator|.
name|class
argument_list|)
decl_stmt|;
comment|// journal initialization
DECL|field|JOURNALS_FILE_BUILTIN
specifier|public
specifier|static
specifier|final
name|String
name|JOURNALS_FILE_BUILTIN
init|=
literal|"/journals/journalList.txt"
decl_stmt|;
DECL|field|JOURNALS_IEEE_ABBREVIATION_LIST_WITH_CODE
specifier|public
specifier|static
specifier|final
name|String
name|JOURNALS_IEEE_ABBREVIATION_LIST_WITH_CODE
init|=
literal|"/journals/IEEEJournalListCode.txt"
decl_stmt|;
DECL|field|JOURNALS_IEEE_ABBREVIATION_LIST_WITH_TEXT
specifier|public
specifier|static
specifier|final
name|String
name|JOURNALS_IEEE_ABBREVIATION_LIST_WITH_TEXT
init|=
literal|"/journals/IEEEJournalListText.txt"
decl_stmt|;
DECL|field|journalAbbrev
specifier|public
specifier|static
name|JournalAbbreviationRepository
name|journalAbbrev
decl_stmt|;
DECL|method|initializeJournalNames (JabRefPreferences jabRefPreferences)
specifier|public
specifier|static
name|void
name|initializeJournalNames
parameter_list|(
name|JabRefPreferences
name|jabRefPreferences
parameter_list|)
block|{
name|journalAbbrev
operator|=
operator|new
name|JournalAbbreviationRepository
argument_list|()
expr_stmt|;
comment|// the order of reading the journal lists is important
comment|// method: last added abbreviation wins
comment|// for instance, in the personal list one can overwrite abbreviations in the built in list
comment|// Read builtin list
name|journalAbbrev
operator|.
name|readJournalListFromResource
argument_list|(
name|JOURNALS_FILE_BUILTIN
argument_list|)
expr_stmt|;
comment|// read IEEE list
if|if
condition|(
name|jabRefPreferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_IEEE_ABRV
argument_list|)
condition|)
block|{
name|journalAbbrev
operator|.
name|readJournalListFromResource
argument_list|(
name|JOURNALS_IEEE_ABBREVIATION_LIST_WITH_CODE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|journalAbbrev
operator|.
name|readJournalListFromResource
argument_list|(
name|JOURNALS_IEEE_ABBREVIATION_LIST_WITH_TEXT
argument_list|)
expr_stmt|;
block|}
comment|// Read external lists
name|List
argument_list|<
name|String
argument_list|>
name|lists
init|=
name|jabRefPreferences
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|EXTERNAL_JOURNAL_LISTS
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|lists
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|Collections
operator|.
name|reverse
argument_list|(
name|lists
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|filename
range|:
name|lists
control|)
block|{
try|try
block|{
name|journalAbbrev
operator|.
name|readJournalListFromFile
argument_list|(
operator|new
name|File
argument_list|(
name|filename
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
comment|// The file couldn't be found... should we tell anyone?
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot find external journal list file "
operator|+
name|filename
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Read personal list
name|String
name|personalJournalList
init|=
name|jabRefPreferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PERSONAL_JOURNAL_LIST
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|personalJournalList
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|personalJournalList
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|journalAbbrev
operator|.
name|readJournalListFromFile
argument_list|(
operator|new
name|File
argument_list|(
name|personalJournalList
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Personal journal list file '"
operator|+
name|personalJournalList
operator|+
literal|"' not found."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|toggleAbbreviation (String text)
specifier|public
specifier|static
name|String
name|toggleAbbreviation
parameter_list|(
name|String
name|text
parameter_list|)
block|{
return|return
name|journalAbbrev
operator|.
name|getNextAbbreviation
argument_list|(
name|text
argument_list|)
operator|.
name|orElse
argument_list|(
name|text
argument_list|)
return|;
block|}
block|}
end_class

end_unit

