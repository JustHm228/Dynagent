module dynagent.base {

	requires java.base;
	requires transitive java.instrument;
	requires jdk.attach;

	exports com.github.justhm228.dynagent.agent;
	exports com.github.justhm228.dynagent.internal.agent to java.instrument, dynagent.test;
	exports com.github.justhm228.dynagent.internal.file to java.instrument, dynagent.test;

	opens com.github.justhm228.dynagent.agent to dynagent.test;
	opens com.github.justhm228.dynagent.internal.agent to dynagent.test;
	opens com.github.justhm228.dynagent.internal.file to dynagent.test;
}
