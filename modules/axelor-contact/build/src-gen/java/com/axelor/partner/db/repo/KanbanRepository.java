package com.axelor.partner.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.db.Query;
import com.axelor.partner.db.Kanban;

public class KanbanRepository extends JpaRepository<Kanban> {

	public KanbanRepository() {
		super(Kanban.class);
	}

	public Kanban findByName(String name) {
		return Query.of(Kanban.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

