begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
package|;
end_package

begin_class
DECL|class|BibtexEntryTypes
specifier|public
class|class
name|BibtexEntryTypes
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Other"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Article"
return|;
block|}
annotation|@
name|Override
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
literal|"pages"
block|,
literal|"number"
block|,
literal|"month"
block|,
literal|"note"
block|,
comment|//- "volume", "pages", "part", "eid"
block|}
return|;
block|}
annotation|@
name|Override
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
comment|//+ "volume", "pages"
block|}
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, JOURNAL and YEAR"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Booklet"
return|;
block|}
annotation|@
name|Override
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
literal|"address"
block|,
literal|"month"
block|,
literal|"year"
block|,
literal|"note"
comment|//+ "lastchecked"
block|}
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"InBook"
return|;
block|}
annotation|@
name|Override
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
comment|//+ "pages"
block|}
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getRequiredFieldsForCustomization
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"author/editor"
block|,
literal|"title"
block|,
literal|"chapter/pages"
block|,
literal|"year"
block|,
literal|"publisher"
block|}
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
argument_list|)
operator|&&
name|entry
operator|.
name|atLeastOnePresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"editor"
block|}
argument_list|,
name|database
argument_list|)
operator|&&
name|entry
operator|.
name|atLeastOnePresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"chapter"
block|,
literal|"pages"
block|}
argument_list|,
name|database
argument_list|)
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Book"
return|;
block|}
annotation|@
name|Override
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
comment|//+ pages
block|}
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getRequiredFieldsForCustomization
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
literal|"author/editor"
block|}
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE, PUBLISHER, YEAR, and an EDITOR and/or AUTHOR"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
argument_list|)
operator|&&
name|entry
operator|.
name|atLeastOnePresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"author"
block|,
literal|"editor"
block|}
argument_list|,
name|database
argument_list|)
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"InCollection"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, BOOKTITLE, PUBLISHER and YEAR"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|CONFERENCE
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|CONFERENCE
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Conference"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, BOOKTITLE and YEAR"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"InProceedings"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, BOOKTITLE and YEAR"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Proceedings"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE and YEAR"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Manual"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"TechReport"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, INSTITUTION and YEAR"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"MastersThesis"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, SCHOOL and YEAR"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"PhdThesis"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE, SCHOOL and YEAR"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Unpublished"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"AUTHOR, TITLE and NOTE"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|PERIODICAL
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|PERIODICAL
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Periodical"
return|;
block|}
annotation|@
name|Override
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
literal|"language"
block|,
literal|"series"
block|,
literal|"volume"
block|,
literal|"number"
block|,
literal|"organization"
block|,
literal|"month"
block|,
literal|"note"
block|,
literal|"url"
block|}
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE and YEAR"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|PATENT
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|PATENT
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Patent"
return|;
block|}
annotation|@
name|Override
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
literal|"language"
block|,
literal|"assignee"
block|,
literal|"address"
block|,
literal|"type"
block|,
literal|"number"
block|,
literal|"day"
block|,
literal|"dayfiled"
block|,
literal|"month"
block|,
literal|"monthfiled"
block|,
literal|"note"
block|,
literal|"url"
block|}
return|;
block|}
annotation|@
name|Override
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
literal|"nationality"
block|,
literal|"number"
block|,
literal|"year"
block|,
literal|"yearfiled"
block|}
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"NATIONALITY, NUMBER, YEAR or YEARFILED"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
literal|"number"
block|,
literal|"bibtexkey"
block|}
argument_list|,
name|database
argument_list|)
operator|&&
name|entry
operator|.
name|atLeastOnePresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"year"
block|,
literal|"yearfiled"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|STANDARD
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|STANDARD
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Standard"
return|;
block|}
annotation|@
name|Override
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
literal|"language"
block|,
literal|"howpublished"
block|,
literal|"type"
block|,
literal|"number"
block|,
literal|"revision"
block|,
literal|"address"
block|,
literal|"month"
block|,
literal|"year"
block|,
literal|"note"
block|,
literal|"url"
block|}
return|;
block|}
annotation|@
name|Override
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
literal|"organization"
block|,
literal|"institution"
block|}
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
index|[]
name|getRequiredFieldsForCustomization
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"title"
block|,
literal|"organization/institution"
block|}
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"TITLE, ORGANIZATION or INSTITUTION"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
argument_list|)
operator|&&
name|entry
operator|.
name|atLeastOnePresent
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"organization"
block|,
literal|"institution"
block|}
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
DECL|field|ELECTRONIC
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|ELECTRONIC
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Electronic"
return|;
block|}
annotation|@
name|Override
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
literal|"month"
block|,
literal|"year"
block|,
literal|"title"
block|,
literal|"language"
block|,
literal|"howpublished"
block|,
literal|"organization"
block|,
literal|"address"
block|,
literal|"note"
block|,
literal|"url"
block|}
return|;
block|}
annotation|@
name|Override
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
block|{}
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"None"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Misc"
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
block|{}
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"None"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
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
argument_list|,
name|database
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|/** 	     * This type is used for IEEEtran.bst to control various 	     * be repeated or not. Not a very elegant solution, but it works... 	     */
DECL|field|IEEETRANBSTCTL
specifier|public
specifier|static
specifier|final
name|BibtexEntryType
name|IEEETRANBSTCTL
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"IEEEtranBSTCTL"
return|;
block|}
annotation|@
name|Override
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
literal|"ctluse_article_number"
block|,
literal|"ctluse_paper"
block|,
literal|"ctluse_forced_etal"
block|,
literal|"ctlmax_names_forced_etal"
block|,
literal|"ctlnames_show_etal"
block|,
literal|"ctluse_alt_spacing"
block|,
literal|"ctlalt_stretch_factor"
block|,
literal|"ctldash_repeated_names"
block|,
literal|"ctlname_format_string"
block|,
literal|"ctlname_latex_cmd"
block|,
literal|"ctlname_url_prefix"
block|}
return|;
block|}
annotation|@
name|Override
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
block|{}
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"None"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isVisibleAtNewEntryDialog
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
block|}
decl_stmt|;
comment|/** 	     * This type is provided as an emergency choice if the user makes 	     * customization changes that remove the type of an entry. 	     */
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
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"Typeless"
return|;
block|}
annotation|@
name|Override
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
block|{}
return|;
block|}
annotation|@
name|Override
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
block|{}
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"None"
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
decl_stmt|;
block|}
end_class

end_unit

