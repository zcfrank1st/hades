import { Component, OnInit } from '@angular/core';
import { NavigateService } from '../navigate.service';
import { ProjectService } from '../project.service';
import { RestfulService } from '../restful.service';
import { StatusService } from '../status.service';

@Component({
  providers: [NavigateService, ProjectService, RestfulService, StatusService],
  selector: 'app-dispatcher',
  templateUrl: './dispatcher.component.html',
  styleUrls: ['./dispatcher.component.css']
})
export class DispatcherComponent implements OnInit {
  public disable = false;
  private path;

  constructor(
    private navigateService: NavigateService,
    private projectService: ProjectService,
    private restfulService: RestfulService,
    private statusService: StatusService) {}

  ngOnInit() {
    this.projectService.currentUrl().then((res) => {
      if (!this.projectService.isProjectPage(res)) {
        this.navigateService.jump2Target('blank');
      }

      console.log("dispatcher " + res);
      this.path = res;

      let data;
      this.restfulService.existsProject(
        this.projectService.projectName(this.path),
        this.path,
        this.statusService.retrieve("PRIVATE-KEY"),
        this.statusService.retrieve("USER-NAME"))
      .subscribe(
      message => data = message,
      error => console.log(error));

      if (data.body === true) {
        this.navigateService.jump2Target("manage");
      } else {
        if (data.code !== 0) {
          this.disable = true;
        }
      }
    });
  }

  createProjectConfigSkeleton() {
    this.restfulService.initProjectSkeleton(
      this.projectService.projectName(this.path),
      this.path,
      this.statusService.retrieve("PRIVATE-KEY"),
      this.statusService.retrieve("USER-NAME"))
    .subscribe(
      message => console.log(message),
      error => console.log(error)
    );
    this.navigateService.jump2Target("manage");
  }
}
