begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
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
name|Objects
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
name|Globals
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
name|logic
operator|.
name|util
operator|.
name|io
operator|.
name|FileUtil
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
name|JabRef
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
name|gui
operator|.
name|FileListEntry
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
name|gui
operator|.
name|FileListTableModel
import|;
end_import

begin_comment
comment|/**  * Search class for files.<br>  *<br>  * This class provides some functionality to search in a {@link BibtexDatabase} for  * files.<br>  *<br>  *  *  * @author Nosh&Dan  * @version 09.11.2008 | 21:21:41  *  */
end_comment

begin_class
DECL|class|DatabaseFileLookup
class|class
name|DatabaseFileLookup
block|{
DECL|field|KEY_FILE_FIELD
specifier|private
specifier|static
specifier|final
name|String
name|KEY_FILE_FIELD
init|=
literal|"file"
decl_stmt|;
DECL|field|fileToFound
specifier|private
specifier|final
name|HashMap
argument_list|<
name|File
argument_list|,
name|Boolean
argument_list|>
name|fileToFound
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|entries
specifier|private
specifier|final
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
decl_stmt|;
DECL|field|possibleFilePaths
specifier|private
specifier|final
name|String
index|[]
name|possibleFilePaths
decl_stmt|;
comment|/**      * Creates an instance by passing a {@link BibtexDatabase} which will be      * used for the searches.      *      * @param aDatabase      *            A {@link BibtexDatabase}.      */
DECL|method|DatabaseFileLookup (BibtexDatabase aDatabase)
specifier|public
name|DatabaseFileLookup
parameter_list|(
name|BibtexDatabase
name|aDatabase
parameter_list|)
block|{
if|if
condition|(
name|aDatabase
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Passing a 'null' BibtexDatabase."
argument_list|)
throw|;
block|}
name|entries
operator|=
name|aDatabase
operator|.
name|getEntries
argument_list|()
expr_stmt|;
name|possibleFilePaths
operator|=
name|JabRef
operator|.
name|jrf
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns whether the File<code>aFile</code> is present in the database      * as an attached File to an {@link BibtexEntry}.<br>      *<br>      * To do this, the field specified by the key<b>file</b> will be searched      * for the provided file for every {@link BibtexEntry} in the database.<br>      *<br>      * For the matching, the absolute file paths will be used.      *      * @param aFile      *            A {@link File} Object.      * @return<code>true</code>, if the file Object is stored in at least one      *         entry in the database, otherwise<code>false</code>.      */
DECL|method|lookupDatabase (File aFile)
specifier|public
name|boolean
name|lookupDatabase
parameter_list|(
name|File
name|aFile
parameter_list|)
block|{
if|if
condition|(
name|fileToFound
operator|.
name|containsKey
argument_list|(
name|aFile
argument_list|)
condition|)
block|{
return|return
name|fileToFound
operator|.
name|get
argument_list|(
name|aFile
argument_list|)
return|;
block|}
else|else
block|{
name|Boolean
name|res
init|=
literal|false
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
if|if
condition|(
name|lookupEntry
argument_list|(
name|aFile
argument_list|,
name|entry
argument_list|)
condition|)
block|{
name|res
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
name|fileToFound
operator|.
name|put
argument_list|(
name|aFile
argument_list|,
name|res
argument_list|)
expr_stmt|;
comment|//System.out.println(aFile);
return|return
name|res
return|;
block|}
block|}
comment|/**      * Searches the specified {@link BibtexEntry}<code>anEntry</code> for the      * appearance of the specified {@link File}<code>aFile</code>.<br>      *<br>      * Therefore the<i>file</i>-field of the bibtex-entry will be searched for      * the absolute filepath of the searched file.<br>      *<br>      *      * @param aFile      *            A file that is searched in an bibtex-entry.      * @param anEntry      *            A bibtex-entry, in which the file is searched.      * @return<code>true</code>, if the bibtex entry stores the file in its      *<i>file</i>-field, otherwise<code>false</code>.      */
DECL|method|lookupEntry (File aFile, BibtexEntry anEntry)
specifier|private
name|boolean
name|lookupEntry
parameter_list|(
name|File
name|aFile
parameter_list|,
name|BibtexEntry
name|anEntry
parameter_list|)
block|{
if|if
condition|(
operator|(
name|aFile
operator|==
literal|null
operator|)
operator|||
operator|(
name|anEntry
operator|==
literal|null
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|FileListTableModel
name|model
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|String
name|fileField
init|=
name|anEntry
operator|.
name|getField
argument_list|(
name|DatabaseFileLookup
operator|.
name|KEY_FILE_FIELD
argument_list|)
decl_stmt|;
name|model
operator|.
name|setContent
argument_list|(
name|fileField
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|model
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|FileListEntry
name|flEntry
init|=
name|model
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|link
init|=
name|flEntry
operator|.
name|getLink
argument_list|()
decl_stmt|;
if|if
condition|(
name|link
operator|==
literal|null
condition|)
block|{
break|break;
block|}
name|File
name|expandedFilename
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|link
argument_list|,
name|possibleFilePaths
argument_list|)
decl_stmt|;
if|if
condition|(
name|Objects
operator|.
name|equals
argument_list|(
name|expandedFilename
argument_list|,
name|aFile
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

