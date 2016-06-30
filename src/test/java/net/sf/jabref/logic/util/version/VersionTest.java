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
DECL|method|unknownVersion ()
specifier|public
name|void
name|unknownVersion
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
block|}
end_class

end_unit
