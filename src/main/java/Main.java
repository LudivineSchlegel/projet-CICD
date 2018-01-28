import ratpack.exec.Blocking;
import ratpack.hikari.HikariModule;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.groovy.template.TextTemplateModule;
import ratpack.guice.Guice;

import static ratpack.groovy.Groovy.groovyTemplate;
import static javax.measure.unit.SI.KILOGRAM;
import javax.measure.quantity.Mass;
import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;

import java.util.*;
import java.sql.*;

import javax.sql.DataSource;

public class Main {
  public static void main(String... args) throws Exception {
    RatpackServer.start(s -> s
        .serverConfig(c -> c
          .baseDir(BaseDir.find())
          .env())

        .registry(Guice.registry(b -> {
          if (System.getenv("JDBC_DATABASE_URL") != null) {
            b.module(HikariModule.class, conf -> {
              conf.addDataSourceProperty("URL", System.getenv("JDBC_DATABASE_URL"));
              conf.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
            });
          }
          b.module(TextTemplateModule.class, conf -> conf.setStaticallyCompile(true));
        }))

        .handlers(chain -> chain
            .get(ctx -> ctx.render(groovyTemplate("index.html")))

            .get("hello", ctx -> {
			  RelativisticModel.select();
			  Amount<Mass> m = Amount.valueOf("12 GeV").to(KILOGRAM);
			  ctx.render("E=mc^2: 12 GeV = " + m.toString());
			})

            .files(f -> f.dir("public"))
        )
    );
  }
}