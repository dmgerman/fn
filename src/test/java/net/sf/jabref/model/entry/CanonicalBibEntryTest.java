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
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
DECL|class|CanonicalBibEntryTest
specifier|public
class|class
name|CanonicalBibEntryTest
block|{
annotation|@
name|Test
DECL|method|simpleCanonicalRepresentation ()
specifier|public
name|void
name|simpleCanonicalRepresentation
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
literal|"id"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|,
literal|"key"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"abc"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"def"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"hij"
argument_list|)
expr_stmt|;
name|String
name|canonicalRepresentation
init|=
name|CanonicalBibtexEntry
operator|.
name|getCanonicalRepresentation
argument_list|(
name|e
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"@article{key,\n  author = {abc},\n  journal = {hij},\n  title = {def}\n}"
argument_list|,
name|canonicalRepresentation
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|canonicalRepresentationWithNewlines ()
specifier|public
name|void
name|canonicalRepresentationWithNewlines
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
literal|"id"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|,
literal|"key"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
literal|"line 1\nline 2"
argument_list|)
expr_stmt|;
name|String
name|canonicalRepresentation
init|=
name|CanonicalBibtexEntry
operator|.
name|getCanonicalRepresentation
argument_list|(
name|e
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"@article{key,\n  abstract = {line 1\nline 2}\n}"
argument_list|,
name|canonicalRepresentation
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

