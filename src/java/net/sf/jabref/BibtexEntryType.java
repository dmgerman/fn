begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 David Weitzman, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  Note: Modified for use in JabRef.  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_class
DECL|class|BibtexEntryType
specifier|public
specifier|abstract
class|class
name|BibtexEntryType
implements|implements
name|Comparable
block|{
DECL|field|OTHER
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|OTHER
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Other"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[
literal|0
index|]
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[
literal|0
index|]
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
decl_stmt|;
DECL|field|ARTICLE
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|ARTICLE
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Article"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"number"
block|,
literal|"month"
block|,
literal|"eid"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"journal"
block|,
literal|"year"
block|,
literal|"volume"
block|,
literal|"pages"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, JOURNAL and YEAR"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"journal"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|,
literal|"volume"
block|,
literal|"pages"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|BOOKLET
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|BOOKLET
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Booklet"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"howpublished"
block|,
literal|"lastchecked"
block|,
literal|"address"
block|,
literal|"month"
block|,
literal|"year"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"title"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"title"
block|,
literal|"bibtexkey"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|INBOOK
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|INBOOK
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Inbook"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"volume"
block|,
literal|"number"
block|,
literal|"series"
block|,
literal|"type"
block|,
literal|"address"
block|,
literal|"edition"
block|,
literal|"month"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"chapter"
block|,
literal|"pages"
block|,
literal|"title"
block|,
literal|"publisher"
block|,
literal|"year"
block|,
literal|"editor"
block|,
literal|"author"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE, CHAPTER and/or PAGES, PUBLISHER, YEAR, and an "
operator|+
literal|"EDITOR and/or AUTHOR"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"title"
block|,
literal|"publisher"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|)
operator|&&
operator|(
operator|(
operator|(
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|entry
operator|.
name|getField
argument_list|(
literal|"editor"
argument_list|)
operator|!=
literal|null
operator|)
operator|)
operator|&&
operator|(
operator|(
name|entry
operator|.
name|getField
argument_list|(
literal|"chapter"
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
operator|!=
literal|null
operator|)
operator|)
operator|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|BOOK
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|BOOK
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Book"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"volume"
block|,
literal|"number"
block|,
literal|"series"
block|,
literal|"address"
block|,
literal|"edition"
block|,
literal|"month"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"title"
block|,
literal|"publisher"
block|,
literal|"year"
block|,
literal|"editor"
block|,
literal|"author"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE, PUBLISHER, YEAR, and an EDITOR and/or AUTHOR"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"title"
block|,
literal|"publisher"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|)
operator|&&
operator|(
operator|(
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
operator|!=
literal|null
operator|)
operator|||
operator|(
name|entry
operator|.
name|getField
argument_list|(
literal|"editor"
argument_list|)
operator|!=
literal|null
operator|)
operator|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|INCOLLECTION
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|INCOLLECTION
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Incollection"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"editor"
block|,
literal|"volume"
block|,
literal|"number"
block|,
literal|"series"
block|,
literal|"type"
block|,
literal|"chapter"
block|,
literal|"pages"
block|,
literal|"address"
block|,
literal|"edition"
block|,
literal|"month"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"booktitle"
block|,
literal|"publisher"
block|,
literal|"year"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, BOOKTITLE, PUBLISHER and YEAR"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"booktitle"
block|,
literal|"publisher"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|INPROCEEDINGS
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|INPROCEEDINGS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Inproceedings"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"editor"
block|,
literal|"volume"
block|,
literal|"number"
block|,
literal|"series"
block|,
literal|"pages"
block|,
literal|"address"
block|,
literal|"month"
block|,
literal|"organization"
block|,
literal|"publisher"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"booktitle"
block|,
literal|"year"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, BOOKTITLE and YEAR"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"booktitle"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|PROCEEDINGS
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|PROCEEDINGS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Proceedings"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"editor"
block|,
literal|"volume"
block|,
literal|"number"
block|,
literal|"series"
block|,
literal|"address"
block|,
literal|"publisher"
block|,
literal|"note"
block|,
literal|"month"
block|,
literal|"organization"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"title"
block|,
literal|"year"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE and YEAR"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"title"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|MANUAL
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|MANUAL
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Manual"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"organization"
block|,
literal|"address"
block|,
literal|"edition"
block|,
literal|"month"
block|,
literal|"year"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"title"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"title"
block|,
literal|"bibtexkey"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|TECHREPORT
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|TECHREPORT
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Techreport"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"type"
block|,
literal|"number"
block|,
literal|"address"
block|,
literal|"month"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"institution"
block|,
literal|"year"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, INSTITUTION and YEAR"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"institution"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|MASTERSTHESIS
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|MASTERSTHESIS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Mastersthesis"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"type"
block|,
literal|"address"
block|,
literal|"month"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"school"
block|,
literal|"year"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, SCHOOL and YEAR"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"school"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|PHDTHESIS
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|PHDTHESIS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Phdthesis"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"type"
block|,
literal|"address"
block|,
literal|"month"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"school"
block|,
literal|"year"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, SCHOOL and YEAR"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"school"
block|,
literal|"year"
block|,
literal|"bibtexkey"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|UNPUBLISHED
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|UNPUBLISHED
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Unpublished"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"month"
block|,
literal|"year"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE and NOTE"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"note"
block|,
literal|"bibtexkey"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|MISC
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|MISC
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Misc"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"title"
block|,
literal|"howpublished"
block|,
literal|"month"
block|,
literal|"year"
block|,
literal|"note"
block|}
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"None"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"bibtexkey"
block|}
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|/**      * This type is provided as an emergency choice if the user makes      * customization changes that remove the type of an entry.      */
DECL|field|TYPELESS
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|TYPELESS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Typeless"
return|;
block|}
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"None"
return|;
block|}
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
decl_stmt|;
DECL|method|getName ()
specifier|public
specifier|abstract
name|String
name|getName
parameter_list|()
function_decl|;
DECL|method|compareTo (Object o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
operator|(
operator|(
name|BibtexEntryType
operator|)
name|o
operator|)
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getOptionalFields ()
specifier|public
specifier|abstract
name|String
index|[]
name|getOptionalFields
parameter_list|()
function_decl|;
DECL|method|getRequiredFields ()
specifier|public
specifier|abstract
name|String
index|[]
name|getRequiredFields
parameter_list|()
function_decl|;
DECL|method|getGeneralFields ()
specifier|public
name|String
index|[]
name|getGeneralFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"crossref"
block|,
literal|"keywords"
block|,
literal|"doi"
block|,
literal|"url"
block|,
literal|"citeseerurl"
block|,
literal|"pdf"
block|,
literal|"abstract"
block|,
literal|"comment"
block|}
return|;
block|}
DECL|method|describeRequiredFields ()
specifier|public
specifier|abstract
name|String
name|describeRequiredFields
parameter_list|()
function_decl|;
DECL|method|hasAllRequiredFields (BibtexEntry entry)
specifier|public
specifier|abstract
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
function_decl|;
DECL|method|getUtilityFields ()
specifier|public
name|String
index|[]
name|getUtilityFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"search"
block|}
return|;
block|}
DECL|method|isRequired (String field)
specifier|public
name|boolean
name|isRequired
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|String
index|[]
name|req
init|=
name|getRequiredFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|req
operator|==
literal|null
condition|)
return|return
literal|false
return|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|req
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|req
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
DECL|method|isOptional (String field)
specifier|public
name|boolean
name|isOptional
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|String
index|[]
name|opt
init|=
name|getOptionalFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|opt
operator|==
literal|null
condition|)
return|return
literal|false
return|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|opt
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|opt
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
return|return
literal|true
return|;
return|return
literal|false
return|;
block|}
DECL|field|ALL_TYPES
specifier|public
specifier|static
name|TreeMap
name|ALL_TYPES
init|=
operator|new
name|TreeMap
argument_list|()
decl_stmt|;
DECL|field|STANDARD_TYPES
specifier|public
specifier|static
name|TreeMap
name|STANDARD_TYPES
init|=
operator|new
name|TreeMap
argument_list|()
decl_stmt|;
static|static
block|{
comment|// Put the standard entry types into the type map.
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"article"
argument_list|,
name|ARTICLE
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"inbook"
argument_list|,
name|INBOOK
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"book"
argument_list|,
name|BOOK
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"booklet"
argument_list|,
name|BOOKLET
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"incollection"
argument_list|,
name|INCOLLECTION
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"inproceedings"
argument_list|,
name|INPROCEEDINGS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"proceedings"
argument_list|,
name|PROCEEDINGS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"manual"
argument_list|,
name|MANUAL
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"mastersthesis"
argument_list|,
name|MASTERSTHESIS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"phdthesis"
argument_list|,
name|PHDTHESIS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"techreport"
argument_list|,
name|TECHREPORT
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"unpublished"
argument_list|,
name|UNPUBLISHED
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"misc"
argument_list|,
name|MISC
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"other"
argument_list|,
name|OTHER
argument_list|)
expr_stmt|;
comment|// We need a record of the standard types, in case the user wants
comment|// to remove a customized version. Therefore we clone the map.
name|STANDARD_TYPES
operator|=
operator|(
name|TreeMap
operator|)
name|ALL_TYPES
operator|.
name|clone
argument_list|()
expr_stmt|;
block|}
comment|/**      * This method returns the BibtexEntryType for the name of a type,      * or null if it does not exist.      */
DECL|method|getType (String name)
specifier|public
specifier|static
name|BibtexEntryType
name|getType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
comment|//Util.pr("'"+name+"'");
name|Object
name|o
init|=
name|ALL_TYPES
operator|.
name|get
argument_list|(
name|name
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|null
return|;
else|else
return|return
operator|(
name|BibtexEntryType
operator|)
name|o
return|;
block|}
comment|/**      * This method returns the standard BibtexEntryType for the      * name of a type, or null if it does not exist.      */
DECL|method|getStandardType (String name)
specifier|public
specifier|static
name|BibtexEntryType
name|getStandardType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
comment|//Util.pr("'"+name+"'");
name|Object
name|o
init|=
name|STANDARD_TYPES
operator|.
name|get
argument_list|(
name|name
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|==
literal|null
condition|)
return|return
literal|null
return|;
else|else
return|return
operator|(
name|BibtexEntryType
operator|)
name|o
return|;
block|}
comment|/**      * Removes a customized entry type from the type map. If this type      * overrode a standard type, we reinstate the standard one.      *      * @param name The customized entry type to remove.      */
DECL|method|removeType (String name)
specifier|public
specifier|static
name|void
name|removeType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
comment|//BibtexEntryType type = getType(name);
name|String
name|nm
init|=
name|name
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|ALL_TYPES
operator|.
name|remove
argument_list|(
name|nm
argument_list|)
expr_stmt|;
if|if
condition|(
name|STANDARD_TYPES
operator|.
name|get
argument_list|(
name|nm
argument_list|)
operator|!=
literal|null
condition|)
block|{
comment|// In this case the user has removed a customized version
comment|// of a standard type. We reinstate the standard type.
name|ALL_TYPES
operator|.
name|put
argument_list|(
name|nm
argument_list|,
name|STANDARD_TYPES
operator|.
name|get
argument_list|(
name|nm
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Load all custom entry types from preferences. This method is      * called from JabRef when the program starts.      */
DECL|method|loadCustomEntryTypes (JabRefPreferences prefs)
specifier|public
specifier|static
name|void
name|loadCustomEntryTypes
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|int
name|number
init|=
literal|0
decl_stmt|;
name|CustomEntryType
name|type
decl_stmt|;
while|while
condition|(
operator|(
name|type
operator|=
name|prefs
operator|.
name|getCustomEntryType
argument_list|(
name|number
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|ALL_TYPES
operator|.
name|put
argument_list|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|type
argument_list|)
expr_stmt|;
name|number
operator|++
expr_stmt|;
block|}
block|}
comment|/**      * Iterate through all entry types, and store those that are      * custom defined to preferences. This method is called from      * JabRefFrame when the program closes.      */
DECL|method|saveCustomEntryTypes (JabRefPreferences prefs)
specifier|public
specifier|static
name|void
name|saveCustomEntryTypes
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|Iterator
name|i
init|=
name|ALL_TYPES
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|int
name|number
init|=
literal|0
decl_stmt|;
comment|//Vector customTypes = new Vector(10, 10);
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Object
name|o
init|=
name|ALL_TYPES
operator|.
name|get
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|CustomEntryType
condition|)
block|{
comment|// Store this entry type.
name|prefs
operator|.
name|storeCustomEntryType
argument_list|(
operator|(
name|CustomEntryType
operator|)
name|o
argument_list|,
name|number
argument_list|)
expr_stmt|;
name|number
operator|++
expr_stmt|;
block|}
block|}
comment|// Then, if there are more 'old' custom types defined, remove these
comment|// from preferences. This is necessary if the number of custom types
comment|// has decreased.
name|prefs
operator|.
name|purgeCustomEntryTypes
argument_list|(
name|number
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

