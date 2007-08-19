begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexEntryType
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

begin_class
DECL|class|ParserResult
specifier|public
class|class
name|ParserResult
block|{
DECL|field|INVALID_FORMAT
specifier|public
specifier|static
name|ParserResult
name|INVALID_FORMAT
init|=
operator|new
name|ParserResult
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
decl_stmt|;
DECL|field|base
specifier|private
name|BibtexDatabase
name|base
decl_stmt|;
DECL|field|metaData
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|metaData
decl_stmt|;
DECL|field|entryTypes
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntryType
argument_list|>
name|entryTypes
decl_stmt|;
DECL|field|file
specifier|private
name|File
name|file
init|=
literal|null
decl_stmt|;
DECL|field|warnings
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|warnings
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|encoding
specifier|private
name|String
name|encoding
init|=
literal|null
decl_stmt|;
comment|// Which encoding was used?
DECL|field|jabrefVersion
specifier|private
name|String
name|jabrefVersion
init|=
literal|null
decl_stmt|;
comment|// Which JabRef version wrote the file, if any?
DECL|field|jabrefMajorVersion
DECL|field|jabrefMinorVersion
specifier|private
name|int
name|jabrefMajorVersion
init|=
literal|0
decl_stmt|,
name|jabrefMinorVersion
init|=
literal|0
decl_stmt|;
comment|// Numeric version representation
DECL|field|toOpenTab
specifier|private
name|boolean
name|toOpenTab
init|=
literal|false
decl_stmt|;
DECL|method|ParserResult (Collection<BibtexEntry> entries)
specifier|public
name|ParserResult
parameter_list|(
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
parameter_list|)
block|{
name|this
argument_list|(
name|ImportFormatReader
operator|.
name|createDatabase
argument_list|(
name|entries
argument_list|)
argument_list|,
literal|null
argument_list|,
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntryType
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|ParserResult (BibtexDatabase base, HashMap<String, String> metaData, HashMap<String, BibtexEntryType> entryTypes)
specifier|public
name|ParserResult
parameter_list|(
name|BibtexDatabase
name|base
parameter_list|,
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|metaData
parameter_list|,
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntryType
argument_list|>
name|entryTypes
parameter_list|)
block|{
name|this
operator|.
name|base
operator|=
name|base
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
name|this
operator|.
name|entryTypes
operator|=
name|entryTypes
expr_stmt|;
block|}
comment|/**      * Check if this base is marked to be added to the currently open tab. Default is false.      * @return      */
DECL|method|toOpenTab ()
specifier|public
name|boolean
name|toOpenTab
parameter_list|()
block|{
return|return
name|toOpenTab
return|;
block|}
DECL|method|setToOpenTab (boolean toOpenTab)
specifier|public
name|void
name|setToOpenTab
parameter_list|(
name|boolean
name|toOpenTab
parameter_list|)
block|{
name|this
operator|.
name|toOpenTab
operator|=
name|toOpenTab
expr_stmt|;
block|}
comment|/**      * Find which version of JabRef, if any, produced this bib file.      * @return The version number string, or null if no JabRef signature could be read.      */
DECL|method|getJabrefVersion ()
specifier|public
name|String
name|getJabrefVersion
parameter_list|()
block|{
return|return
name|jabrefVersion
return|;
block|}
comment|/**      * Set the JabRef version number string for this parser result.      * @param jabrefVersion The version number string.                                               */
DECL|method|setJabrefVersion (String jabrefVersion)
specifier|public
name|void
name|setJabrefVersion
parameter_list|(
name|String
name|jabrefVersion
parameter_list|)
block|{
name|this
operator|.
name|jabrefVersion
operator|=
name|jabrefVersion
expr_stmt|;
block|}
DECL|method|getJabrefMajorVersion ()
specifier|public
name|int
name|getJabrefMajorVersion
parameter_list|()
block|{
return|return
name|jabrefMajorVersion
return|;
block|}
DECL|method|setJabrefMajorVersion (int jabrefMajorVersion)
specifier|public
name|void
name|setJabrefMajorVersion
parameter_list|(
name|int
name|jabrefMajorVersion
parameter_list|)
block|{
name|this
operator|.
name|jabrefMajorVersion
operator|=
name|jabrefMajorVersion
expr_stmt|;
block|}
DECL|method|getJabrefMinorVersion ()
specifier|public
name|int
name|getJabrefMinorVersion
parameter_list|()
block|{
return|return
name|jabrefMinorVersion
return|;
block|}
DECL|method|setJabrefMinorVersion (int jabrefMinorVersion)
specifier|public
name|void
name|setJabrefMinorVersion
parameter_list|(
name|int
name|jabrefMinorVersion
parameter_list|)
block|{
name|this
operator|.
name|jabrefMinorVersion
operator|=
name|jabrefMinorVersion
expr_stmt|;
block|}
DECL|method|getDatabase ()
specifier|public
name|BibtexDatabase
name|getDatabase
parameter_list|()
block|{
return|return
name|base
return|;
block|}
DECL|method|getMetaData ()
specifier|public
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getMetaData
parameter_list|()
block|{
return|return
name|metaData
return|;
block|}
DECL|method|getEntryTypes ()
specifier|public
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntryType
argument_list|>
name|getEntryTypes
parameter_list|()
block|{
return|return
name|entryTypes
return|;
block|}
DECL|method|getFile ()
specifier|public
name|File
name|getFile
parameter_list|()
block|{
return|return
name|file
return|;
block|}
DECL|method|setFile (File f)
specifier|public
name|void
name|setFile
parameter_list|(
name|File
name|f
parameter_list|)
block|{
name|file
operator|=
name|f
expr_stmt|;
block|}
comment|/**      * Sets the variable indicating which encoding was used during parsing.      *      * @param enc String the name of the encoding.      */
DECL|method|setEncoding (String enc)
specifier|public
name|void
name|setEncoding
parameter_list|(
name|String
name|enc
parameter_list|)
block|{
name|encoding
operator|=
name|enc
expr_stmt|;
block|}
comment|/**      * Returns the name of the encoding used during parsing, or null if not specified      * (indicates that prefs.get("defaultEncoding") was used).      */
DECL|method|getEncoding ()
specifier|public
name|String
name|getEncoding
parameter_list|()
block|{
return|return
name|encoding
return|;
block|}
comment|/**      * Add a parser warning.      *      * @param s String Warning text. Must be pretranslated. Only added if there isn't already a dupe.      */
DECL|method|addWarning (String s)
specifier|public
name|void
name|addWarning
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
operator|!
name|warnings
operator|.
name|contains
argument_list|(
name|s
argument_list|)
condition|)
name|warnings
operator|.
name|add
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
DECL|method|hasWarnings ()
specifier|public
name|boolean
name|hasWarnings
parameter_list|()
block|{
return|return
operator|(
name|warnings
operator|.
name|size
argument_list|()
operator|>
literal|0
operator|)
return|;
block|}
DECL|method|warnings ()
specifier|public
name|String
index|[]
name|warnings
parameter_list|()
block|{
name|String
index|[]
name|s
init|=
operator|new
name|String
index|[
name|warnings
operator|.
name|size
argument_list|()
index|]
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
name|warnings
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
name|s
index|[
name|i
index|]
operator|=
name|warnings
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
return|return
name|s
return|;
block|}
block|}
end_class

end_unit

