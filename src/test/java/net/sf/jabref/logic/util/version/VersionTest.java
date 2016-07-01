begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util.version
package|package
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
name|version
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
name|logic
operator|.
name|util
operator|.
name|BuildInfo
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
name|Version
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
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
name|assertTrue
import|;
end_import

begin_class
DECL|class|VersionTest
specifier|public
class|class
name|VersionTest
block|{
annotation|@
name|Test
DECL|method|unknownVersionAsString ()
specifier|public
name|void
name|unknownVersionAsString
parameter_list|()
block|{
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|unknownVersionAsNull ()
specifier|public
name|void
name|unknownVersionAsNull
parameter_list|()
block|{
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|unknownVersionAsEmptyString ()
specifier|public
name|void
name|unknownVersionAsEmptyString
parameter_list|()
block|{
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|initVersionFromWrongStringResultsInUnknownVersion ()
specifier|public
name|void
name|initVersionFromWrongStringResultsInUnknownVersion
parameter_list|()
block|{
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
literal|"${version}"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionOneDigit ()
specifier|public
name|void
name|versionOneDigit
parameter_list|()
block|{
name|String
name|versionText
init|=
literal|"1"
decl_stmt|;
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
name|versionText
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|versionText
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|version
operator|.
name|getMajor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|version
operator|.
name|getMinor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|version
operator|.
name|getPatch
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|version
operator|.
name|isDevelopmentVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionTwoDigits ()
specifier|public
name|void
name|versionTwoDigits
parameter_list|()
block|{
name|String
name|versionText
init|=
literal|"1.2"
decl_stmt|;
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
name|versionText
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|versionText
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|version
operator|.
name|getMajor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|version
operator|.
name|getMinor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|version
operator|.
name|getPatch
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|version
operator|.
name|isDevelopmentVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionThreeDigits ()
specifier|public
name|void
name|versionThreeDigits
parameter_list|()
block|{
name|String
name|versionText
init|=
literal|"1.2.3"
decl_stmt|;
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
name|versionText
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|versionText
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|version
operator|.
name|getMajor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|version
operator|.
name|getMinor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|version
operator|.
name|getPatch
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|version
operator|.
name|isDevelopmentVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionOneDigitDevVersion ()
specifier|public
name|void
name|versionOneDigitDevVersion
parameter_list|()
block|{
name|String
name|versionText
init|=
literal|"1dev"
decl_stmt|;
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
name|versionText
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|versionText
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|version
operator|.
name|getMajor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|version
operator|.
name|getMinor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|version
operator|.
name|getPatch
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|version
operator|.
name|isDevelopmentVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionTwoDigitDevVersion ()
specifier|public
name|void
name|versionTwoDigitDevVersion
parameter_list|()
block|{
name|String
name|versionText
init|=
literal|"1.2dev"
decl_stmt|;
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
name|versionText
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|versionText
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|version
operator|.
name|getMajor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|version
operator|.
name|getMinor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|version
operator|.
name|getPatch
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|version
operator|.
name|isDevelopmentVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionThreeDigitDevVersion ()
specifier|public
name|void
name|versionThreeDigitDevVersion
parameter_list|()
block|{
name|String
name|versionText
init|=
literal|"1.2.3dev"
decl_stmt|;
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
name|versionText
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|versionText
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|version
operator|.
name|getMajor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|version
operator|.
name|getMinor
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|version
operator|.
name|getPatch
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|version
operator|.
name|isDevelopmentVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|validVersionIsNotNewerThanUnknownVersion ()
specifier|public
name|void
name|validVersionIsNotNewerThanUnknownVersion
parameter_list|()
block|{
comment|// Reason: unknown version should only happen for developer builds where we don't want an update notification
name|Version
name|unknownVersion
init|=
operator|new
name|Version
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|)
decl_stmt|;
name|Version
name|validVersion
init|=
operator|new
name|Version
argument_list|(
literal|"4.2"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|validVersion
operator|.
name|isNewerThan
argument_list|(
name|unknownVersion
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|unknownVersionIsNotNewerThanvalidVersion ()
specifier|public
name|void
name|unknownVersionIsNotNewerThanvalidVersion
parameter_list|()
block|{
name|Version
name|unknownVersion
init|=
operator|new
name|Version
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|)
decl_stmt|;
name|Version
name|validVersion
init|=
operator|new
name|Version
argument_list|(
literal|"4.2"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|unknownVersion
operator|.
name|isNewerThan
argument_list|(
name|validVersion
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionNewerThan ()
specifier|public
name|void
name|versionNewerThan
parameter_list|()
block|{
name|Version
name|olderVersion
init|=
operator|new
name|Version
argument_list|(
literal|"2.4"
argument_list|)
decl_stmt|;
name|Version
name|newerVersion
init|=
operator|new
name|Version
argument_list|(
literal|"4.2"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|newerVersion
operator|.
name|isNewerThan
argument_list|(
name|olderVersion
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionNotNewerThan ()
specifier|public
name|void
name|versionNotNewerThan
parameter_list|()
block|{
name|Version
name|olderVersion
init|=
operator|new
name|Version
argument_list|(
literal|"2.4"
argument_list|)
decl_stmt|;
name|Version
name|newerVersion
init|=
operator|new
name|Version
argument_list|(
literal|"4.2"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|olderVersion
operator|.
name|isNewerThan
argument_list|(
name|newerVersion
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionNotNewerThanSameVersion ()
specifier|public
name|void
name|versionNotNewerThanSameVersion
parameter_list|()
block|{
name|Version
name|version1
init|=
operator|new
name|Version
argument_list|(
literal|"4.2"
argument_list|)
decl_stmt|;
name|Version
name|version2
init|=
operator|new
name|Version
argument_list|(
literal|"4.2"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|version1
operator|.
name|isNewerThan
argument_list|(
name|version2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionNewerThanDevTwoDigits ()
specifier|public
name|void
name|versionNewerThanDevTwoDigits
parameter_list|()
block|{
name|Version
name|older
init|=
operator|new
name|Version
argument_list|(
literal|"4.2"
argument_list|)
decl_stmt|;
name|Version
name|newer
init|=
operator|new
name|Version
argument_list|(
literal|"4.3dev"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|newer
operator|.
name|isNewerThan
argument_list|(
name|older
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionNewerThanDevThreeDigits ()
specifier|public
name|void
name|versionNewerThanDevThreeDigits
parameter_list|()
block|{
name|Version
name|older
init|=
operator|new
name|Version
argument_list|(
literal|"4.2.1"
argument_list|)
decl_stmt|;
name|Version
name|newer
init|=
operator|new
name|Version
argument_list|(
literal|"4.3dev"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|newer
operator|.
name|isNewerThan
argument_list|(
name|older
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionNewerMinor ()
specifier|public
name|void
name|versionNewerMinor
parameter_list|()
block|{
name|Version
name|older
init|=
operator|new
name|Version
argument_list|(
literal|"4.1"
argument_list|)
decl_stmt|;
name|Version
name|newer
init|=
operator|new
name|Version
argument_list|(
literal|"4.2.1"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|newer
operator|.
name|isNewerThan
argument_list|(
name|older
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionNotNewerMinor ()
specifier|public
name|void
name|versionNotNewerMinor
parameter_list|()
block|{
name|Version
name|older
init|=
operator|new
name|Version
argument_list|(
literal|"4.1"
argument_list|)
decl_stmt|;
name|Version
name|newer
init|=
operator|new
name|Version
argument_list|(
literal|"4.2.1"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|older
operator|.
name|isNewerThan
argument_list|(
name|newer
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionNewerPatch ()
specifier|public
name|void
name|versionNewerPatch
parameter_list|()
block|{
name|Version
name|older
init|=
operator|new
name|Version
argument_list|(
literal|"4.2.1"
argument_list|)
decl_stmt|;
name|Version
name|newer
init|=
operator|new
name|Version
argument_list|(
literal|"4.2.2"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|newer
operator|.
name|isNewerThan
argument_list|(
name|older
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionNotNewerPatch ()
specifier|public
name|void
name|versionNotNewerPatch
parameter_list|()
block|{
name|Version
name|older
init|=
operator|new
name|Version
argument_list|(
literal|"4.2.1"
argument_list|)
decl_stmt|;
name|Version
name|newer
init|=
operator|new
name|Version
argument_list|(
literal|"4.2.2"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|older
operator|.
name|isNewerThan
argument_list|(
name|newer
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|equalVersionsNotNewer ()
specifier|public
name|void
name|equalVersionsNotNewer
parameter_list|()
block|{
name|Version
name|version1
init|=
operator|new
name|Version
argument_list|(
literal|"4.2.2"
argument_list|)
decl_stmt|;
name|Version
name|version2
init|=
operator|new
name|Version
argument_list|(
literal|"4.2.2"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|version1
operator|.
name|isNewerThan
argument_list|(
name|version2
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|changelogWithTwoDigits ()
specifier|public
name|void
name|changelogWithTwoDigits
parameter_list|()
block|{
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
literal|"3.4"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"https://github.com/JabRef/jabref/blob/v3.4/CHANGELOG.md"
argument_list|,
name|version
operator|.
name|getChangelogUrl
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|changelogWithThreeDigits ()
specifier|public
name|void
name|changelogWithThreeDigits
parameter_list|()
block|{
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
literal|"3.4.1"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"https://github.com/JabRef/jabref/blob/v3.4.1/CHANGELOG.md"
argument_list|,
name|version
operator|.
name|getChangelogUrl
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionNull ()
specifier|public
name|void
name|versionNull
parameter_list|()
block|{
name|String
name|versionText
init|=
literal|null
decl_stmt|;
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
name|versionText
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|versionEmpty ()
specifier|public
name|void
name|versionEmpty
parameter_list|()
block|{
name|String
name|versionText
init|=
literal|""
decl_stmt|;
name|Version
name|version
init|=
operator|new
name|Version
argument_list|(
name|versionText
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BuildInfo
operator|.
name|UNKNOWN_VERSION
argument_list|,
name|version
operator|.
name|getFullVersion
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

