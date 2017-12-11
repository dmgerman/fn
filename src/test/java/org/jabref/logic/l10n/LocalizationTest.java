begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.l10n
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
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
DECL|class|LocalizationTest
specifier|public
class|class
name|LocalizationTest
block|{
DECL|field|locale
specifier|private
name|Locale
name|locale
decl_stmt|;
annotation|@
name|Before
DECL|method|storeDefaultLocale ()
specifier|public
name|void
name|storeDefaultLocale
parameter_list|()
block|{
name|locale
operator|=
name|Locale
operator|.
name|getDefault
argument_list|()
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|restoreDefaultLocale ()
specifier|public
name|void
name|restoreDefaultLocale
parameter_list|()
block|{
name|Locale
operator|.
name|setDefault
argument_list|(
name|locale
argument_list|)
expr_stmt|;
name|javax
operator|.
name|swing
operator|.
name|JComponent
operator|.
name|setDefaultLocale
argument_list|(
name|locale
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSetKnownLanguage ()
specifier|public
name|void
name|testSetKnownLanguage
parameter_list|()
block|{
name|Locale
operator|.
name|setDefault
argument_list|(
name|Locale
operator|.
name|CHINA
argument_list|)
expr_stmt|;
name|Localization
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"en"
argument_list|,
name|Locale
operator|.
name|getDefault
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSetUnknownLanguage ()
specifier|public
name|void
name|testSetUnknownLanguage
parameter_list|()
block|{
name|Locale
operator|.
name|setDefault
argument_list|(
name|Locale
operator|.
name|CHINA
argument_list|)
expr_stmt|;
name|Localization
operator|.
name|setLanguage
argument_list|(
literal|"WHATEVER"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"en"
argument_list|,
name|Locale
operator|.
name|getDefault
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testKnownTranslationWithGroups ()
specifier|public
name|void
name|testKnownTranslationWithGroups
parameter_list|()
block|{
name|Localization
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|String
name|knownKey
init|=
literal|"Groups"
decl_stmt|;
name|assertEquals
argument_list|(
name|knownKey
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|knownValueWithMnemonics
init|=
literal|"&Groups"
decl_stmt|;
name|assertEquals
argument_list|(
name|knownValueWithMnemonics
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testKnownEnglishTranslationOfUndo ()
specifier|public
name|void
name|testKnownEnglishTranslationOfUndo
parameter_list|()
block|{
name|Localization
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|String
name|knownKey
init|=
literal|"Undo"
decl_stmt|;
name|assertEquals
argument_list|(
name|knownKey
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|knownValueWithMnemonics
init|=
literal|"&Undo"
decl_stmt|;
name|assertEquals
argument_list|(
name|knownValueWithMnemonics
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testKnownGermanTranslationDoesNotHaveAmpersand ()
specifier|public
name|void
name|testKnownGermanTranslationDoesNotHaveAmpersand
parameter_list|()
block|{
name|Localization
operator|.
name|setLanguage
argument_list|(
literal|"de"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Alle speichern"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save all"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testKnownGermanTranslation ()
specifier|public
name|void
name|testKnownGermanTranslation
parameter_list|()
block|{
name|Localization
operator|.
name|setLanguage
argument_list|(
literal|"de"
argument_list|)
expr_stmt|;
name|String
name|knownKey
init|=
literal|"Save all"
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Alle speichern"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A&lle speichern"
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testKnownTranslationWithCountryModifier ()
specifier|public
name|void
name|testKnownTranslationWithCountryModifier
parameter_list|()
block|{
name|Localization
operator|.
name|setLanguage
argument_list|(
literal|"en_US"
argument_list|)
expr_stmt|;
name|String
name|knownKey
init|=
literal|"Groups"
decl_stmt|;
name|assertEquals
argument_list|(
name|knownKey
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|knownValueWithMnemonics
init|=
literal|"&Groups"
decl_stmt|;
name|assertEquals
argument_list|(
name|knownValueWithMnemonics
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUnknownTranslation ()
specifier|public
name|void
name|testUnknownTranslation
parameter_list|()
block|{
name|Localization
operator|.
name|setLanguage
argument_list|(
literal|"en"
argument_list|)
expr_stmt|;
name|String
name|knownKey
init|=
literal|"WHATEVER"
decl_stmt|;
name|assertEquals
argument_list|(
name|knownKey
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|knownKey
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUnsetLanguageTranslation ()
specifier|public
name|void
name|testUnsetLanguageTranslation
parameter_list|()
block|{
name|String
name|knownKey
init|=
literal|"Groups"
decl_stmt|;
name|assertEquals
argument_list|(
name|knownKey
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|knownValueWithMnemonics
init|=
literal|"&Groups"
decl_stmt|;
name|assertEquals
argument_list|(
name|knownValueWithMnemonics
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
name|knownKey
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

