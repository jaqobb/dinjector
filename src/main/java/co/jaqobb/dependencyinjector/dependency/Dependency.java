package co.jaqobb.dependencyinjector.dependency;

import co.jaqobb.dependencyinjector.exception.MissingShorthandNotationInfoException;
import co.jaqobb.dependencyinjector.repository.Repository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Class that represents a dependency.
 */
public final class Dependency {
	/**
	 * Group id of the dependency
	 */
	private final String groupId;
	/**
	 * Artifact id of the dependency.
	 */
	private final String artifactId;
	/**
	 * Version of the dependency.
	 */
	private final String version;
	/**
	 * Repository of the dependency.
	 */
	private final Repository repository;

	/**
	 * Constructs a new Dependency class instance with the given shorthand notation. Maven central repository will be used as a repository.
	 *
	 * @param shorthandNotation A shorthand notation (<group id>:<artifact id>:<version>).
	 *
	 * @throws NullPointerException If the given shorthand notation is null.
	 * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id or version.
	 */
	public Dependency(String shorthandNotation) {
		this(shorthandNotation, Repositories.MAVEN_CENTRAL);
	}

	/**
	 * Constructs a new Dependency class instance with the given shorthand notation and repository.
	 *
	 * @param shorthandNotation A shorthand notation (<group id>:<artifact id>:<version>).
	 * @param repository A repository which holds dependency with the given group id, arifact id, and version.
	 *
	 * @throws NullPointerException If the given shorthand notation or repository is null.
	 * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id or version.
	 */
	public Dependency(String shorthandNotation, String repository) {
		Objects.requireNonNull(shorthandNotation, "shorthandNotation");
		Objects.requireNonNull(repository, "repository");
		String[] data = shorthandNotation.split(":");
		if (data.length != 3) {
			throw new MissingShorthandNotationInfoException("Shorthand notation must have only group id, artifact id and version separated by ':'");
		}
		this.groupId = data[0];
		this.artifactId = data[1];
		this.version = data[2];
		this.repository = new Repository(repository);
	}

	/**
	 * Constructs a new Dependency class instance with, the given shorthand notation and repository.
	 *
	 * @param shorthandNotation A shorthand notation (<group id>:<artifact id>:<version>).
	 * @param repository A repository which holds dependency with the given group id, arifact id, and version.
	 *
	 * @throws NullPointerException If the given shorthand notation or repository is null.
	 * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id or version.
	 */
	public Dependency(String shorthandNotation, Repository repository) {
		Objects.requireNonNull(shorthandNotation, "shorthandNotation");
		Objects.requireNonNull(repository, "repository");
		String[] data = shorthandNotation.split(":");
		if (data.length != 3) {
			throw new MissingShorthandNotationInfoException("Shorthand notation must have only group id, artifact id and version separated by ':'");
		}
		this.groupId = data[0];
		this.artifactId = data[1];
		this.version = data[2];
		this.repository = repository;
	}

	/**
	 * Constructs a new Dependency class instance with, the given group id, artfact id, and version. Maven central repository will be used as a repository.
	 *
	 * @param groupId A group id of the dependency.
	 * @param artifactId An artifact id of the dependency.
	 * @param version A version of the dependency.
	 *
	 * @throws NullPointerException If the given group id, artifact id or version is null.
	 */
	public Dependency(String groupId, String artifactId, String version) {
		this(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
	}

	/**
	 * Constructs a new Dependency class instance with the given group id, artifact id, version, and repository.
	 *
	 * @param groupId A group id of the dependency.
	 * @param artifactId An artifact id of the dependency.
	 * @param version A version of the dependency.
	 * @param repository A repository which holds dependency with the given group id, artifact id, and version.
	 *
	 * @throws NullPointerException If the given group id, artifact id, version or repository is null.
	 */
	public Dependency(String groupId, String artifactId, String version, String repository) {
		this(groupId, artifactId, version, new Repository(repository));
	}

	/**
	 * Constructs a new Dependency class instance with, the given group id, artifact id, version, and repository.
	 *
	 * @param groupId A group id of the dependency.
	 * @param artifactId An artifact id of the dependency.
	 * @param version A version of the dependency.
	 * @param repository A repository which holds dependency with the given group id, artifact id, and version.
	 *
	 * @throws NullPointerException If the given group id, artifact id, version or repository is null.
	 */
	public Dependency(String groupId, String artifactId, String version, Repository repository) {
		this.groupId = Objects.requireNonNull(groupId, "groupId");
		this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
		this.version = Objects.requireNonNull(version, "version");
		this.repository = Objects.requireNonNull(repository, "repository");
	}

	/**
	 * Returns this dependency group id.
	 *
	 * @return This dependency group id.
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * Returns this dependency artifact id.
	 *
	 * @return This dependency artifact id.
	 */
	public String getArtifactId() {
		return this.artifactId;
	}

	/**
	 * Returns this dependency version.
	 *
	 * @return This dependency version.
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * Returns this dependency repository.
	 *
	 * @return This dependency repository.
	 */
	public Repository getRepository() {
		return this.repository;
	}

	/**
	 * Returns this dependency download url.
	 *
	 * @return This dependency download url.
	 */
	public URL getDownloadUrl() throws MalformedURLException {
		String url = this.repository.getUrl();
		if (!url.endsWith("/")) {
			url += "/";
		}
		String groupId = this.groupId.replace(".", "/");
		return new URL(url + groupId + "/" + this.artifactId + "/" + this.version + "/" + this.artifactId + "-" + this.version + ".jar");
	}

	/**
	 * Returns true if the given object is the same as this class, and false otherwise.
	 *
	 * @param object An object to check.
	 *
	 * @return True if the given object is the same as this class, and false otherwise.
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || this.getClass() != object.getClass()) {
			return false;
		}
		Dependency that = (Dependency) object;
		return Objects.equals(this.groupId, that.groupId) && Objects.equals(this.artifactId, that.artifactId) && Objects.equals(this.version, that.version) && Objects.equals(this.repository, that.repository);
	}

	/**
	 * Returns a hash code of this class.
	 *
	 * @return A hash code of this class.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.groupId, this.artifactId, this.version, this.repository);
	}

	/**
	 * Returns a nice looking representation of this class.
	 *
	 * @return A nice looking representation of this class.
	 */
	@Override
	public String toString() {
		return "Dependency{" + "groupId=" + this.groupId + ", artifactId=" + this.artifactId + ", version=" + this.version + ", repository=" + this.repository + "}";
	}
}