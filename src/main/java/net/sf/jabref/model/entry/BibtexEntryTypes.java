begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_comment
comment|/**  * This class represents all supported BibTex entry types.  *<p>  * Article, Book, Booklet, Conference, Inbook, Incollection, Inproceedings,  * Manual, Mastersthesis, Misc, Phdthesis, Proceedings, Techreport, Unpublished  */
end_comment

begin_class
DECL|class|BibtexEntryTypes
specifier|public
class|class
name|BibtexEntryTypes
block|{
comment|/**      * An article from a journal or magazine.      *<p>      * Required fields: author, title, journal, year.      * Optional fields: volume, number, pages, month, note.      */
DECL|field|ARTICLE
specifier|public
specifier|static
specifier|final
name|EntryType
name|ARTICLE
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"journal"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"pages"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * A book with an explicit publisher.      *<p>      * Required fields: author or editor, title, publisher, year.      * Optional fields: volume or number, series, address, edition, month, note.      */
DECL|field|BOOK
specifier|public
specifier|static
specifier|final
name|EntryType
name|BOOK
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"title"
argument_list|,
literal|"publisher"
argument_list|,
literal|"year"
argument_list|,
literal|"author/editor"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"address"
argument_list|,
literal|"edition"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * A work that is printed and bound, but without a named publisher or sponsoring institution.      *<p>      * Required field: title.      * Optional fields: author, howpublished, address, month, year, note.      */
DECL|field|BOOKLET
specifier|public
specifier|static
specifier|final
name|EntryType
name|BOOKLET
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"title"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"author"
argument_list|,
literal|"howpublished"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"year"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * An article in a conference proceedings.      *<p>      * Required fields: author, title, booktitle, year.      * Optional fields: editor, volume or number, series, pages, address, month, organization, publisher, note.      */
DECL|field|CONFERENCE
specifier|public
specifier|static
specifier|final
name|EntryType
name|CONFERENCE
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"booktitle"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"editor"
argument_list|,
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"pages"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"organization"
argument_list|,
literal|"publisher"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * A part of a book, which may be a chapter (or section or whatever) and/or a range of pages.      *<p>      * Required fields: author or editor, title, chapter and/or pages, publisher, year.      * Optional fields: volume or number, series, type, address, edition, month, note.      */
DECL|field|INBOOK
specifier|public
specifier|static
specifier|final
name|EntryType
name|INBOOK
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"chapter/pages"
argument_list|,
literal|"title"
argument_list|,
literal|"publisher"
argument_list|,
literal|"year"
argument_list|,
literal|"author/editor"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"type"
argument_list|,
literal|"address"
argument_list|,
literal|"edition"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * A part of a book having its own title.      * Required fields: author, title, booktitle, publisher, year.      * Optional fields: editor, volume or number, series, type, chapter, pages, address, edition, month, note.      */
DECL|field|INCOLLECTION
specifier|public
specifier|static
specifier|final
name|EntryType
name|INCOLLECTION
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"booktitle"
argument_list|,
literal|"publisher"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"editor"
argument_list|,
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"type"
argument_list|,
literal|"chapter"
argument_list|,
literal|"pages"
argument_list|,
literal|"address"
argument_list|,
literal|"edition"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * An article in a conference proceedings.      *<p>      * Required fields: author, title, booktitle, year.      * Optional fields: editor, volume or number, series, pages, address, month, organization, publisher, note.      */
DECL|field|INPROCEEDINGS
specifier|public
specifier|static
specifier|final
name|EntryType
name|INPROCEEDINGS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"booktitle"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"editor"
argument_list|,
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"pages"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"organization"
argument_list|,
literal|"publisher"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * Technical documentation.      * Required field: title.      * Optional fields: author, organization, address, edition, month, year, note.      */
DECL|field|MANUAL
specifier|public
specifier|static
specifier|final
name|EntryType
name|MANUAL
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"title"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"author"
argument_list|,
literal|"organization"
argument_list|,
literal|"address"
argument_list|,
literal|"edition"
argument_list|,
literal|"month"
argument_list|,
literal|"year"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * A Master's thesis.      *<p>      * Required fields: author, title, school, year.      * Optional fields: type, address, month, note.      */
DECL|field|MASTERSTHESIS
specifier|public
specifier|static
specifier|final
name|EntryType
name|MASTERSTHESIS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"school"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"type"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * Use this type when nothing else fits.      *<p>      * Required fields: none.      * Optional fields: author, title, howpublished, month, year, note.      */
DECL|field|MISC
specifier|public
specifier|static
specifier|final
name|EntryType
name|MISC
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllOptional
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"howpublished"
argument_list|,
literal|"month"
argument_list|,
literal|"year"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * A PhD thesis.      *<p>      * Required fields: author, title, school, year.      * Optional fields: type, address, month, note.      */
DECL|field|PHDTHESIS
specifier|public
specifier|static
specifier|final
name|EntryType
name|PHDTHESIS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"school"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"type"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * The proceedings of a conference.      *<p>      * Required fields: title, year.      * Optional fields: editor, volume or number, series, address, month, organization, publisher, note.      */
DECL|field|PROCEEDINGS
specifier|public
specifier|static
specifier|final
name|EntryType
name|PROCEEDINGS
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"title"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"editor"
argument_list|,
literal|"volume"
argument_list|,
literal|"number"
argument_list|,
literal|"series"
argument_list|,
literal|"address"
argument_list|,
literal|"publisher"
argument_list|,
literal|"note"
argument_list|,
literal|"month"
argument_list|,
literal|"organization"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * A report published by a school or other institution, usually numbered within a series.      *<p>      * Required fields: author, title, institution, year.      * Optional fields: type, number, address, month, note.      */
DECL|field|TECHREPORT
specifier|public
specifier|static
specifier|final
name|EntryType
name|TECHREPORT
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"institution"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"type"
argument_list|,
literal|"number"
argument_list|,
literal|"address"
argument_list|,
literal|"month"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
comment|/**      * A document having an author and title, but not formally published.      *<p>      * Required fields: author, title, note.      * Optional fields: month, year.      */
DECL|field|UNPUBLISHED
specifier|public
specifier|static
specifier|final
name|EntryType
name|UNPUBLISHED
init|=
operator|new
name|BibtexEntryType
argument_list|()
block|{
block|{
name|addAllRequired
argument_list|(
literal|"author"
argument_list|,
literal|"title"
argument_list|,
literal|"note"
argument_list|)
expr_stmt|;
name|addAllOptional
argument_list|(
literal|"month"
argument_list|,
literal|"year"
argument_list|)
expr_stmt|;
block|}
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
block|}
decl_stmt|;
DECL|field|ALL
specifier|public
specifier|static
specifier|final
name|List
argument_list|<
name|EntryType
argument_list|>
name|ALL
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|ARTICLE
argument_list|,
name|INBOOK
argument_list|,
name|BOOK
argument_list|,
name|BOOKLET
argument_list|,
name|INCOLLECTION
argument_list|,
name|CONFERENCE
argument_list|,
name|INPROCEEDINGS
argument_list|,
name|PROCEEDINGS
argument_list|,
name|MANUAL
argument_list|,
name|MASTERSTHESIS
argument_list|,
name|PHDTHESIS
argument_list|,
name|TECHREPORT
argument_list|,
name|UNPUBLISHED
argument_list|,
name|MISC
argument_list|)
decl_stmt|;
DECL|method|getType (String name)
specifier|public
specifier|static
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|getType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
name|ALL
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|e
lambda|->
name|e
operator|.
name|getName
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|name
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
return|;
block|}
block|}
end_class

end_unit

