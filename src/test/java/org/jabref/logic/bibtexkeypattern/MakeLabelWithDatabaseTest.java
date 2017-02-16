begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.bibtexkeypattern
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtexkeypattern
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|bibtexkeypattern
operator|.
name|DatabaseBibtexKeyPattern
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|bibtexkeypattern
operator|.
name|GlobalBibtexKeyPattern
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|MakeLabelWithDatabaseTest
specifier|public
class|class
name|MakeLabelWithDatabaseTest
block|{
DECL|field|database
specifier|private
name|BibDatabase
name|database
decl_stmt|;
DECL|field|preferences
specifier|private
name|BibtexKeyPatternPreferences
name|preferences
decl_stmt|;
DECL|field|pattern
specifier|private
name|GlobalBibtexKeyPattern
name|pattern
decl_stmt|;
DECL|field|bibtexKeyPattern
specifier|private
name|DatabaseBibtexKeyPattern
name|bibtexKeyPattern
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|database
operator|=
operator|new
name|BibDatabase
argument_list|()
expr_stmt|;
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"An awesome paper on JabRef"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|pattern
operator|=
name|GlobalBibtexKeyPattern
operator|.
name|fromPattern
argument_list|(
literal|"[auth][year]"
argument_list|)
expr_stmt|;
name|bibtexKeyPattern
operator|=
operator|new
name|DatabaseBibtexKeyPattern
argument_list|(
name|pattern
argument_list|)
expr_stmt|;
name|preferences
operator|=
operator|new
name|BibtexKeyPatternPreferences
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|pattern
argument_list|,
literal|','
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKey ()
specifier|public
name|void
name|generateDefaultKey
parameter_list|()
block|{
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Doe2016"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKeyAlreadyExistsDuplicatesStartAtA ()
specifier|public
name|void
name|generateDefaultKeyAlreadyExistsDuplicatesStartAtA
parameter_list|()
block|{
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|BibEntry
name|entry2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry2
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry2
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Doe2016a"
argument_list|)
argument_list|,
name|entry2
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKeyAlwaysLetter ()
specifier|public
name|void
name|generateDefaultKeyAlwaysLetter
parameter_list|()
block|{
name|preferences
operator|=
operator|new
name|BibtexKeyPatternPreferences
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|pattern
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Doe2016a"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKeyAlwaysLetterAlreadyExistsDuplicatesStartAtB ()
specifier|public
name|void
name|generateDefaultKeyAlwaysLetterAlreadyExistsDuplicatesStartAtB
parameter_list|()
block|{
name|preferences
operator|=
operator|new
name|BibtexKeyPatternPreferences
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|pattern
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|BibEntry
name|entry2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry2
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry2
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Doe2016b"
argument_list|)
argument_list|,
name|entry2
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKeyStartDuplicatesAtB ()
specifier|public
name|void
name|generateDefaultKeyStartDuplicatesAtB
parameter_list|()
block|{
name|preferences
operator|=
operator|new
name|BibtexKeyPatternPreferences
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|pattern
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Doe2016"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKeyAlreadyExistsDuplicatesStartAtB ()
specifier|public
name|void
name|generateDefaultKeyAlreadyExistsDuplicatesStartAtB
parameter_list|()
block|{
name|preferences
operator|=
operator|new
name|BibtexKeyPatternPreferences
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|pattern
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|BibEntry
name|entry2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry2
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry2
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Doe2016b"
argument_list|)
argument_list|,
name|entry2
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKeyAlreadyExistsManyDuplicates ()
specifier|public
name|void
name|generateDefaultKeyAlreadyExistsManyDuplicates
parameter_list|()
block|{
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|BibEntry
name|entry2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry2
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|setCiteKey
argument_list|(
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry2
argument_list|)
expr_stmt|;
name|BibEntry
name|entry3
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry3
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe"
argument_list|)
expr_stmt|;
name|entry3
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|entry3
operator|.
name|setCiteKey
argument_list|(
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry3
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry3
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Doe2016a"
argument_list|)
argument_list|,
name|entry3
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKeyFirstTwoAlreadyExists ()
specifier|public
name|void
name|generateDefaultKeyFirstTwoAlreadyExists
parameter_list|()
block|{
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|BibEntry
name|entry2
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry2
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe"
argument_list|)
expr_stmt|;
name|entry2
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry2
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry2
argument_list|)
expr_stmt|;
name|BibEntry
name|entry3
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry3
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe"
argument_list|)
expr_stmt|;
name|entry3
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|entry3
operator|.
name|setCiteKey
argument_list|(
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry3
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry3
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Doe2016b"
argument_list|)
argument_list|,
name|entry3
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKeyLowerModified ()
specifier|public
name|void
name|generateDefaultKeyLowerModified
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[auth:lower][year]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"doe2016"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKeyUpperModified ()
specifier|public
name|void
name|generateDefaultKeyUpperModified
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[auth:upper][year]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"DOE2016"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateDefaultKeyFixedValue ()
specifier|public
name|void
name|generateDefaultKeyFixedValue
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[auth]Test[year]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"DoeTest2016"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyShortYear ()
specifier|public
name|void
name|generateKeyShortYear
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[shortyear]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"16"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyAuthN ()
specifier|public
name|void
name|generateKeyAuthN
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[auth2]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Do"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyAuthNShortName ()
specifier|public
name|void
name|generateKeyAuthNShortName
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[auth10]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Doe"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyEmptyField ()
specifier|public
name|void
name|generateKeyEmptyField
parameter_list|()
block|{
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyEmptyFieldDefaultText ()
specifier|public
name|void
name|generateKeyEmptyFieldDefaultText
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[author:(No Author Provided)]"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|clearField
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"NoAuthorProvided"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyEmptyFieldColonInDefaultText ()
specifier|public
name|void
name|generateKeyEmptyFieldColonInDefaultText
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[author:(Problem:No Author Provided)]"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|clearField
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Problem:NoAuthorProvided"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyTitle ()
specifier|public
name|void
name|generateKeyTitle
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[title]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"AnawesomepaperonJabRef"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyTitleAbbr ()
specifier|public
name|void
name|generateKeyTitleAbbr
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[title:abbr]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"AapoJ"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyShorttitle ()
specifier|public
name|void
name|generateKeyShorttitle
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[shorttitle]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"awesomepaperJabRef"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyVeryshorttitle ()
specifier|public
name|void
name|generateKeyVeryshorttitle
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[veryshorttitle]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"awesome"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyShorttitleINI ()
specifier|public
name|void
name|generateKeyShorttitleINI
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[shorttitleINI]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"apJ"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyAuthNM ()
specifier|public
name|void
name|generateKeyAuthNM
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[auth4_3]"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe and Donald Smith and Will Wonder"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Wond"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyAuthNMLargeN ()
specifier|public
name|void
name|generateKeyAuthNMLargeN
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[auth20_3]"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe and Donald Smith and Will Wonder"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Wonder"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyAuthNMLargeM ()
specifier|public
name|void
name|generateKeyAuthNMLargeM
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[auth2_4]"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe and Donald Smith and Will Wonder"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyAuthNMLargeMReallyReturnsEmptyString ()
specifier|public
name|void
name|generateKeyAuthNMLargeMReallyReturnsEmptyString
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[auth2_4][year]"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe and Donald Smith and Will Wonder"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2016"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyRegExReplace ()
specifier|public
name|void
name|generateKeyRegExReplace
parameter_list|()
block|{
name|preferences
operator|=
operator|new
name|BibtexKeyPatternPreferences
argument_list|(
literal|"2"
argument_list|,
literal|"3"
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|pattern
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[auth][year]"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe and Donald Smith and Will Wonder"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Doe3016"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyAuthIni ()
specifier|public
name|void
name|generateKeyAuthIni
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[authIni2]"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe and Donald Smith and Will Wonder"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"DS"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyAuthIniMany ()
specifier|public
name|void
name|generateKeyAuthIniMany
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[authIni10]"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"John Doe and Donald Smith and Will Wonder"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"DoeSmiWon"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyTitleTitleCase ()
specifier|public
name|void
name|generateKeyTitleTitleCase
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[title:title_case]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"AnAwesomePaperonJabref"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyTitleCapitalize ()
specifier|public
name|void
name|generateKeyTitleCapitalize
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[title:capitalize]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"AnAwesomePaperOnJabref"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyTitleSentenceCase ()
specifier|public
name|void
name|generateKeyTitleSentenceCase
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[title:sentence_case]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Anawesomepaperonjabref"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyTitleTitleCaseAbbr ()
specifier|public
name|void
name|generateKeyTitleTitleCaseAbbr
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[title:title_case:abbr]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"AAPoJ"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyTitleCapitalizeAbbr ()
specifier|public
name|void
name|generateKeyTitleCapitalizeAbbr
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[title:capitalize:abbr]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"AAPOJ"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|generateKeyTitleSentenceCaseAbbr ()
specifier|public
name|void
name|generateKeyTitleSentenceCaseAbbr
parameter_list|()
block|{
name|bibtexKeyPattern
operator|.
name|setDefaultValue
argument_list|(
literal|"[title:sentence_case:abbr]"
argument_list|)
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeAndSetLabel
argument_list|(
name|bibtexKeyPattern
argument_list|,
name|database
argument_list|,
name|entry
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Aapoj"
argument_list|)
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

